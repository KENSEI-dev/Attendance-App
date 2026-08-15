package com.attendance.app.ui.attendance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.attendance.app.data.AttendanceStatus
import com.attendance.app.databinding.ItemAttendanceBinding

/** One row of the "mark today's attendance" list. */
data class AttendanceRowUi(
    val subjectId: Long,
    val name: String,
    val code: String?,
    val statusToday: String?,   // null if not marked yet today
    val percentage: Double?     // overall attendance % for this subject, null if no data
)

class AttendanceAdapter(
    private val onMark: (subjectId: Long, status: String) -> Unit
) : ListAdapter<AttendanceRowUi, AttendanceAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<AttendanceRowUi>() {
        override fun areItemsTheSame(old: AttendanceRowUi, new: AttendanceRowUi) = old.subjectId == new.subjectId
        override fun areContentsTheSame(old: AttendanceRowUi, new: AttendanceRowUi) = old == new
    }

    inner class ViewHolder(val binding: ItemAttendanceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = getItem(position)
        val b = holder.binding

        b.subjectName.text = row.name
        b.subjectPercentage.text = row.percentage?.let { "%.1f%%".format(it) } ?: "No data"

        // reflect today's marked status, if any, via button selection state
        b.presentButton.isSelected = row.statusToday == AttendanceStatus.PRESENT
        b.absentButton.isSelected = row.statusToday == AttendanceStatus.ABSENT
        b.cancelledButton.isSelected = row.statusToday == AttendanceStatus.CANCELLED

        b.presentButton.setOnClickListener { onMark(row.subjectId, AttendanceStatus.PRESENT) }
        b.absentButton.setOnClickListener { onMark(row.subjectId, AttendanceStatus.ABSENT) }
        b.cancelledButton.setOnClickListener { onMark(row.subjectId, AttendanceStatus.CANCELLED) }
    }
}
