package com.attendance.app.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.app.data.AttendanceAggregation
import com.attendance.app.data.DayStatus
import com.attendance.app.data.HolidayRules
import com.attendance.app.repository.AttendanceRepository
import com.attendance.app.ui.attendance.AttendanceRowUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * Backs the Calendar tab: pick any past date (or today) and mark/edit
 * attendance for it — the screen for catching up on missed or forgotten
 * days. This deliberately does NOT introduce any new kind of data: it calls
 * the exact same AttendanceRepository.markAttendance / markHoliday /
 * unmarkHoliday that the Attendance tab calls for "today". Those already
 * took an arbitrary date parameter from the start — the only thing missing
 * was a UI to pick a date other than today. Because of that, anything
 * marked here writes to the same local DB row, the same sync log entry,
 * the same next GitHub publish, and the same widget matrix as if it had
 * been marked on the correct day originally — "reflected everywhere" is a
 * consequence of the existing architecture, not new plumbing.
 */
class CalendarViewModel(
    private val repository: AttendanceRepository,
    private val localDeviceId: String
) : ViewModel() {

    private val isoFormat = DateTimeFormatter.ISO_LOCAL_DATE

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _visibleMonth = MutableStateFlow(YearMonth.from(LocalDate.now()))
    val visibleMonth: StateFlow<YearMonth> = _visibleMonth.asStateFlow()

    /** No marking future attendance — silently ignored rather than erroring, since the grid already dims/disables future cells. */
    fun selectDate(date: LocalDate) {
        if (date.isAfter(LocalDate.now())) return
        _selectedDate.value = date
    }

    fun goToPreviousMonth() {
        _visibleMonth.value = _visibleMonth.value.minusMonths(1)
    }

    /** Can't navigate past the current month — there's nothing to mark there yet. */
    fun goToNextMonth() {
        val next = _visibleMonth.value.plusMonths(1)
        if (!next.isAfter(YearMonth.from(LocalDate.now()))) _visibleMonth.value = next
    }

    /** Aggregated per-day status for the visible month — same rule (absent > present > cancelled) the widget and JSON export already use. */
    private val monthDayStatuses: StateFlow<Map<LocalDate, DayStatus>> = _visibleMonth.flatMapLatest { month ->
        val start = month.atDay(1).format(isoFormat)
        val end = month.atEndOfMonth().format(isoFormat)
        repository.getAttendanceBetween(start, end).map { records -> AttendanceAggregation.aggregateByDay(records) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val monthHolidays: StateFlow<Set<LocalDate>> = _visibleMonth.flatMapLatest { month ->
        repository.getHolidays().map { holidays ->
            holidays.mapNotNull { runCatching { LocalDate.parse(it.date, isoFormat) }.getOrNull() }
                .filter { YearMonth.from(it) == month }
                .toSet()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    /** The full grid for the currently visible month: leading blanks + one cell per day, fully re-derived whenever any input changes. */
    val calendarDays: StateFlow<List<CalendarDayUi>> = combine(
        _visibleMonth, _selectedDate, monthDayStatuses, monthHolidays
    ) { month, selected, statuses, holidays ->
        val firstOfMonth = month.atDay(1)
        // Sunday(java=7) -> 0 .. Saturday(6) -> 6 — same mapping the widget's
        // matrix renderer uses, so "day of week" means the same thing everywhere.
        val leadingBlanks = firstOfMonth.dayOfWeek.value % 7
        val today = LocalDate.now()

        val blanks = List(leadingBlanks) {
            CalendarDayUi(null, isSelected = false, isToday = false, isFuture = false, isHoliday = false, status = null)
        }
        val days = (1..month.lengthOfMonth()).map { dayNum ->
            val date = month.atDay(dayNum)
            CalendarDayUi(
                date = date,
                isSelected = date == selected,
                isToday = date == today,
                isFuture = date.isAfter(today),
                isHoliday = HolidayRules.isWeekend(date) || holidays.contains(date),
                status = statuses[date]
            )
        }
        blanks + days
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** True when the selected date is a holiday — either explicitly marked, or (new) any Saturday/Sunday via [HolidayRules]. */
    val isSelectedDateHoliday: StateFlow<Boolean> = _selectedDate.flatMapLatest { date ->
        repository.getHolidays().map { list -> HolidayRules.isHoliday(date, list) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    /** Whether the holiday toggle button should be shown at all — hidden on a weekend, see [HolidayRules.isOverridable]. */
    val isSelectedDateHolidayOverridable: StateFlow<Boolean> = _selectedDate
        .map { date -> HolidayRules.isOverridable(date) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    /** Same shape as AttendanceViewModel.rows — just for the selected date instead of always "today". */
    val rows: StateFlow<List<AttendanceRowUi>> = _selectedDate.flatMapLatest { date ->
        val dateStr = date.format(isoFormat)
        combine(
            repository.getSubjects(),
            repository.getAttendanceForDate(dateStr),
            repository.getSubjectSummaries()
        ) { subjects, records, summaries ->
            val statusBySubject: Map<Long, String> = records
                .groupBy { it.subjectId }
                .mapValues { (_, recs) ->
                    val ownRecord = recs.firstOrNull { it.deviceId == localDeviceId }
                    (ownRecord ?: recs.maxByOrNull { it.createdAt })!!.status
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
            repository.markAttendance(subjectId, _selectedDate.value.format(isoFormat), status)
        }
    }

    fun toggleSelectedDateHoliday() {
        viewModelScope.launch {
            val date = _selectedDate.value
            if (!HolidayRules.isOverridable(date)) return@launch // defense in depth — the button should already be hidden for this case
            val dateStr = date.format(isoFormat)
            if (repository.getHoliday(dateStr) != null) {
                repository.unmarkHoliday(dateStr)
            } else {
                repository.markHoliday(dateStr)
            }
        }
    }
}
