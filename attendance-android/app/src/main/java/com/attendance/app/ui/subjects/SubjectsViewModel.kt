package com.attendance.app.ui.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.app.data.SubjectEntity
import com.attendance.app.repository.AttendanceRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SubjectsViewModel(private val repository: AttendanceRepository) : ViewModel() {

    val subjects: StateFlow<List<SubjectEntity>> = repository.getSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addSubject(name: String, code: String?) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.addSubject(name, code)
        }
    }

    fun deleteSubject(subject: SubjectEntity) {
        viewModelScope.launch {
            repository.deleteSubject(subject)
        }
    }
}
