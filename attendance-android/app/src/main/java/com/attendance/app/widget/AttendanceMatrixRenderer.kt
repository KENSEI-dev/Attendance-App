package com.attendance.app.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.attendance.app.R
import com.attendance.app.data.AttendanceAggregation
import com.attendance.app.data.AttendanceEntity
import com.attendance.app.data.DayStatus
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Renders the same GitHub-contribution-style dot matrix as the Session 1
 * terminal tool, but as an Android Bitmap — RemoteViews can't host a custom
 * View or run arbitrary Canvas drawing live, so the widget draws the grid
 * once per refresh and displays it as a plain ImageView.
 *
 * The day-status aggregation rule (absent > present > cancelled priority)
 * lives in [AttendanceAggregation] and is shared with the Session 5 GitHub
 * JSON export, so the widget and the published dashboard can't drift apart
 * on what a given day's dot means. That rule is still this project's own
 * read of "GitHub-style" — the Session 1 CLI source wasn't part of the
 * zips handed off between sessions, so a side-by-side check against the
 * terminal tool's actual output is still worth doing when you get a chance.
 */
object AttendanceMatrixRenderer {

    private const val ROWS = 7 // Sun..Sat, like GitHub
    private const val MIN_WEEKS = 4
    private const val MAX_WEEKS = 20
    private const val DEFAULT_WEEKS = 12

    /**
     * @param widgetWidthDp approximate current widget width, from
     *   AppWidgetManager's OPTION_APPWIDGET_MIN_WIDTH — used only to decide
     *   how many weeks fit; falls back to [DEFAULT_WEEKS] if unavailable.
     */
    fun render(context: Context, records: List<AttendanceEntity>, widgetWidthDp: Int): Bitmap {
        val density = context.resources.displayMetrics.density
        val cellDp = 8f
        val gapDp = 3f
        val paddingDp = 6f
        val stepDp = cellDp + gapDp

        val weeks = if (widgetWidthDp > 0) {
            val usableDp = widgetWidthDp - (paddingDp * 2)
            (usableDp / stepDp).toInt().coerceIn(MIN_WEEKS, MAX_WEEKS)
        } else {
            DEFAULT_WEEKS
        }

        val today = LocalDate.now()
        val totalDays = weeks * ROWS
        val startDate = today.minusDays((totalDays - 1).toLong())

        val statusByDate = AttendanceAggregation.aggregateByDay(records)

        val stepPx = stepDp * density
        val cellPx = cellDp * density
        val paddingPx = paddingDp * density
        val widthPx = (paddingPx * 2 + weeks * stepPx - gapDp * density).toInt().coerceAtLeast(1)
        val heightPx = (paddingPx * 2 + ROWS * stepPx - gapDp * density).toInt().coerceAtLeast(1)

        val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val presentColor = ContextCompat.getColor(context, R.color.present_green)
        val absentColor = ContextCompat.getColor(context, R.color.absent_red)
        val cancelledColor = ContextCompat.getColor(context, R.color.cancelled_grey)
        val emptyColor = ContextCompat.getColor(context, R.color.empty_dot)

        var cursor = startDate
        while (!cursor.isAfter(today)) {
            val daysFromStart = ChronoUnit.DAYS.between(startDate, cursor).toInt()
            val col = daysFromStart / ROWS
            val row = cursor.dayOfWeek.value % 7 // Sunday(java=7) -> 0, Monday(1) -> 1, ... Saturday(6) -> 6

            val status = statusByDate[cursor]
            paint.color = when (status) {
                DayStatus.PRESENT -> presentColor
                DayStatus.ABSENT -> absentColor
                DayStatus.CANCELLED -> cancelledColor
                null -> emptyColor
            }

            val left = paddingPx + col * stepPx
            val top = paddingPx + row * stepPx
            canvas.drawRoundRect(
                left, top, left + cellPx, top + cellPx,
                cellPx * 0.3f, cellPx * 0.3f, paint
            )

            cursor = cursor.plusDays(1)
        }

        return bitmap
    }
}
