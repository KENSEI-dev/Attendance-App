package com.attendance.app.ui.subjects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.attendance.app.data.SubjectEntity
import com.attendance.app.databinding.ItemSubjectBinding

class SubjectsAdapter(
    private val onDelete: (SubjectEntity) -> Unit
) : ListAdapter<SubjectEntity, SubjectsAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<SubjectEntity>() {
        override fun areItemsTheSame(old: SubjectEntity, new: SubjectEntity) = old.id == new.id
        override fun areContentsTheSame(old: SubjectEntity, new: SubjectEntity) = old == new
    }

    inner class ViewHolder(val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subject = getItem(position)
        holder.binding.subjectName.text = subject.name
        holder.binding.subjectCode.text = subject.code ?: ""
        holder.binding.deleteButton.setOnClickListener { onDelete(subject) }
    }
}
