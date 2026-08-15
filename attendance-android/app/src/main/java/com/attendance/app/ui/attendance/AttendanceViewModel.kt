package com.attendance.app.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.app.repository.AttendanceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AttendanceViewModel(
    private val repository: AttendanceRepository,
    private val localDeviceId: String
) : ViewModel() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val today: String = dateFormat.format(Date())

    /** One row per subject: today's marked status (if any) + overall attendance %. */
    val rows: StateFlow<List<AttendanceRowUi>> = combine(
        repository.getSubjects(),
        repository.getAttendanceForDate(today),
        repository.getSubjectSummaries()
    ) { subjects, todaysRecords, summaries ->
        // A subject can have more than one record for the same day once
        // sync is in play (e.g. marked from two devices). Prefer this
        // device's own record; otherwise fall back to the most recent one
        // by timestamp. See AttendanceRepository's merge functions for the
        // full explanation of this trade-off.
        val statusBySubject: Map<Long, String> = todaysRecords
            .groupBy { it.subjectId }
            .mapValues { (_, records) ->
                val ownRecord = records.firstOrNull { it.deviceId == localDeviceId }
                (ownRecord ?: records.maxByOrNull { it.createdAt })!!.status
            }
        val percentageBySubject = summaries.associate { it.subjectId to it.percentage }

        subjects.map { subject ->
            AttendanceRowUi(
                subjectId = subject.id,
                name = subject.name,
                code = subject.code,
                statusToday = statusBySubject[subject.id],
                percentage = percentageBySubject[subject.id]
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun mark(subjectId: Long, status: String) {
        viewModelScope.launch {
            repository.markAttendance(subjectId, today, status)
        }
    }

    fun clear(subjectId: Long) {
        viewModelScope.launch {
            repository.clearAttendance(subjectId, today)
        }
    }
}
