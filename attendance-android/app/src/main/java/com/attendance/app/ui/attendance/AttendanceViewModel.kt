package com.attendance.app.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.app.repository.AttendanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
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
    private fun computeToday(): String = dateFormat.format(Date())

    /**
     * Was a fixed `val` computed once at ViewModel construction — harmless
     * normally (a ViewModel is usually recreated by the time a new day
     * actually starts), but it meant the app silently kept marking against
     * yesterday's date if the process stayed alive across midnight (or, as
     * found while testing: across a manual clock jump with no restart in
     * between). Now a StateFlow, rechecked explicitly via [refreshToday] —
     * call that from the Fragment's onResume — and [rows] re-subscribes to
     * the correct date's data whenever it changes instead of being locked
     * to whatever date the ViewModel happened to be born on.
     */
    private val todayFlow = MutableStateFlow(computeToday())
    val today: StateFlow<String> = todayFlow.asStateFlow()

    /** Re-checks the system date and updates [today] if a day has actually passed. Safe to call often — no-op if unchanged. */
    fun refreshToday() {
        val current = computeToday()
        if (current != todayFlow.value) todayFlow.value = current
    }

    /** One row per subject: today's marked status (if any) + overall attendance %. */
    val rows: StateFlow<List<AttendanceRowUi>> = todayFlow.flatMapLatest { todayDate ->
        combine(
            repository.getSubjects(),
            repository.getAttendanceForDate(todayDate),
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
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun mark(subjectId: Long, status: String) {
        viewModelScope.launch {
            repository.markAttendance(subjectId, todayFlow.value, status)
        }
    }

    fun clear(subjectId: Long) {
        viewModelScope.launch {
            repository.clearAttendance(subjectId, todayFlow.value)
        }
    }
}
