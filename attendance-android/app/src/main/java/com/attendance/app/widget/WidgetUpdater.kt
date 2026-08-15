package com.attendance.app.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import com.attendance.app.MainActivity
import com.attendance.app.R
import com.attendance.app.data.AppDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * All the widget-update logic lives here (not in AttendanceWidgetProvider)
 * so the periodic WorkManager worker can call the exact same code path —
 * there's no AppWidgetProvider instance available from inside a Worker.
 */
object WidgetUpdater {

    private const val LOOKBACK_DAYS = 20L * 7L // matches renderer's MAX_WEEKS, gives it enough data at any widget size
    private val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

    suspend fun updateAllWidgets(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val ids = appWidgetManager.getAppWidgetIds(
            ComponentName(context, AttendanceWidgetProvider::class.java)
        )
        for (id in ids) {
            updateWidget(context, appWidgetManager, id)
        }
    }

    /**
     * Session 7: the whole body used to run with no error handling — a
     * corrupted DB read or a bad widget-width value from the launcher would
     * throw inside a bare `CoroutineScope(Dispatchers.IO).launch {}` in the
     * provider, which has no exception handler and would crash the process,
     * not just the widget. Now any failure here falls back to a plain
     * "couldn't load" widget instead of taking the app down.
     */
    suspend fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = try {
            buildContentViews(context, appWidgetManager, appWidgetId)
        } catch (e: Exception) {
            Log.e("WidgetUpdater", "Failed to build widget content for id=$appWidgetId", e)
            buildErrorViews(context)
        }

        // Click intents work the same whether content loaded or not, so they're
        // wired up unconditionally rather than duplicated in both branches above.
        val openAppIntent = Intent(context, MainActivity::class.java)
        views.setOnClickPendingIntent(
            R.id.widget_root,
            PendingIntent.getActivity(context, appWidgetId, openAppIntent, pendingIntentFlags())
        )
        val refreshIntent = Intent(context, AttendanceWidgetProvider::class.java).apply {
            action = AttendanceWidgetProvider.ACTION_MANUAL_REFRESH
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        views.setOnClickPendingIntent(
            R.id.widget_refresh_button,
            PendingIntent.getBroadcast(context, appWidgetId, refreshIntent, pendingIntentFlags())
        )

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private suspend fun buildContentViews(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int
    ): RemoteViews {
        val db = AppDatabase.getInstance(context)
        val sinceDate = LocalDate.now().minusDays(LOOKBACK_DAYS).format(DateTimeFormatter.ISO_LOCAL_DATE)

        val records = db.attendanceDao().getSince(sinceDate)
        val overall = db.attendanceDao().getOverallCounts()

        val options = appWidgetManager.getAppWidgetOptions(appWidgetId)
        val widthDp = options?.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 0) ?: 0

        val bitmap = AttendanceMatrixRenderer.render(context, records, widthDp)

        val views = RemoteViews(context.packageName, R.layout.widget_attendance)
        views.setImageViewBitmap(R.id.widget_matrix_image, bitmap)

        val percentText = overall.percentage?.let { "$it% present" } ?: "No data yet"
        views.setTextViewText(R.id.widget_percentage, percentText)
        val nowText = java.time.LocalTime.now().format(timeFormatter)
        views.setTextViewText(R.id.widget_updated_at, context.getString(R.string.widget_updated_at, nowText))
        return views
    }

    /**
     * Minimal fallback: a fresh RemoteViews inflate of the same layout, just
     * without touching the matrix ImageView — leaving it at whatever the XML
     * layout itself defaults to, rather than passing a null Bitmap into
     * setImageViewBitmap (which some RemoteViews/launcher combos handle
     * inconsistently).
     */
    private fun buildErrorViews(context: Context): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.widget_attendance)
        views.setTextViewText(R.id.widget_percentage, "Couldn't load attendance data")
        views.setTextViewText(R.id.widget_updated_at, "Tap to open the app")
        return views
    }

    private fun pendingIntentFlags(): Int {
        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }
        return flags
    }
}
