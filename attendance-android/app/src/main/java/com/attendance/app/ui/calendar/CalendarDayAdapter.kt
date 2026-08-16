package com.attendance.app.ui.calendar

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.attendance.app.R
import com.attendance.app.data.DayStatus
import com.attendance.app.databinding.ItemCalendarDayBinding
import java.time.LocalDate

/** One cell in the month grid. `date == null` renders as a blank leading-padding cell before the 1st. */
data class CalendarDayUi(
    val date: LocalDate?,
    val isSelected: Boolean,
    val isToday: Boolean,
    val isFuture: Boolean,
    val isHoliday: Boolean,
    val status: DayStatus?
)

/**
 * Plain RecyclerView.Adapter, not ListAdapter/DiffUtil — a month is at most
 * 42 cells, and the whole grid is rebuilt as one unit whenever any input
 * flow changes (see CalendarViewModel.calendarDays), so there's no
 * meaningful partial-update case for DiffUtil to optimize here.
 */
class CalendarDayAdapter(
    private val onDayClick: (LocalDate) -> Unit
) : RecyclerView.Adapter<CalendarDayAdapter.ViewHolder>() {

    private var days: List<CalendarDayUi> = emptyList()

    fun submitList(newDays: List<CalendarDayUi>) {
        days = newDays
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCalendarDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        val circle = holder.binding.dayCircle
        val context = circle.context

        if (day.date == null) {
            circle.text = ""
            circle.background = null
            circle.setOnClickListener(null)
            return
        }

        circle.text = day.date.dayOfMonth.toString()

        val fillColor = when {
            day.isHoliday -> ContextCompat.getColor(context, R.color.holiday_purple)
            day.status == DayStatus.PRESENT -> ContextCompat.getColor(context, R.color.present_green)
            day.status == DayStatus.ABSENT -> ContextCompat.getColor(context, R.color.absent_red)
            day.status == DayStatus.CANCELLED -> ContextCompat.getColor(context, R.color.cancelled_grey)
            else -> ContextCompat.getColor(context, R.color.empty_dot)
        }
        val filled = day.isHoliday || day.status != null
        val textColor = if (filled) {
            ContextCompat.getColor(context, android.R.color.white)
        } else {
            ContextCompat.getColor(context, R.color.text_primary)
        }

        circle.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(fillColor)
            if (day.isSelected) {
                val strokeWidthPx = (2 * context.resources.displayMetrics.density).toInt()
                setStroke(strokeWidthPx, ContextCompat.getColor(context, R.color.primary))
            }
        }
        circle.setTextColor(textColor)
        circle.setTypeface(null, if (day.isToday) Typeface.BOLD else Typeface.NORMAL)
        circle.alpha = if (day.isFuture) 0.35f else 1f

        circle.setOnClickListener {
            if (!day.isFuture) onDayClick(day.date)
        }
    }
}
