package com.attendance.app.ui.calendar;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010$\u001a\u00020%J\u0006\u0010&\u001a\u00020%J\u0016\u0010\'\u001a\u00020%2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0005J\u000e\u0010+\u001a\u00020%2\u0006\u0010,\u001a\u00020\tJ\u0006\u0010-\u001a\u00020%R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\f0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0016\u0010\u0016\u001a\n \n*\u0004\u0018\u00010\u00170\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001a0\u00190\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u001c0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0012R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0012R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0012\u00a8\u0006."}, d2 = {"Lcom/attendance/app/ui/calendar/CalendarViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/attendance/app/repository/AttendanceRepository;", "localDeviceId", "", "(Lcom/attendance/app/repository/AttendanceRepository;Ljava/lang/String;)V", "_selectedDate", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Ljava/time/LocalDate;", "kotlin.jvm.PlatformType", "_visibleMonth", "Ljava/time/YearMonth;", "calendarDays", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/attendance/app/ui/calendar/CalendarDayUi;", "getCalendarDays", "()Lkotlinx/coroutines/flow/StateFlow;", "isSelectedDateHoliday", "", "isSelectedDateHolidayOverridable", "isoFormat", "Ljava/time/format/DateTimeFormatter;", "monthDayStatuses", "", "Lcom/attendance/app/data/DayStatus;", "monthHolidays", "", "rows", "Lcom/attendance/app/ui/attendance/AttendanceRowUi;", "getRows", "selectedDate", "getSelectedDate", "visibleMonth", "getVisibleMonth", "goToNextMonth", "", "goToPreviousMonth", "mark", "subjectId", "", "status", "selectDate", "date", "toggleSelectedDateHoliday", "app_debug"})
public final class CalendarViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.repository.AttendanceRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localDeviceId = null;
    private final java.time.format.DateTimeFormatter isoFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.time.LocalDate> _selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.time.LocalDate> selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.time.YearMonth> _visibleMonth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> visibleMonth = null;
    
    /**
     * Aggregated per-day status for the visible month — same rule (absent > present > cancelled) the widget and JSON export already use.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.time.LocalDate, com.attendance.app.data.DayStatus>> monthDayStatuses = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.time.LocalDate>> monthHolidays = null;
    
    /**
     * The full grid for the currently visible month: leading blanks + one cell per day, fully re-derived whenever any input changes.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.calendar.CalendarDayUi>> calendarDays = null;
    
    /**
     * True when the selected date is a holiday — either explicitly marked, or (new) any Saturday/Sunday via [HolidayRules].
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isSelectedDateHoliday = null;
    
    /**
     * Whether the holiday toggle button should be shown at all — hidden on a weekend, see [HolidayRules.isOverridable].
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isSelectedDateHolidayOverridable = null;
    
    /**
     * Same shape as AttendanceViewModel.rows — just for the selected date instead of always "today".
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.attendance.AttendanceRowUi>> rows = null;
    
    public CalendarViewModel(@org.jetbrains.annotations.NotNull()
    com.attendance.app.repository.AttendanceRepository repository, @org.jetbrains.annotations.NotNull()
    java.lang.String localDeviceId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.time.LocalDate> getSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> getVisibleMonth() {
        return null;
    }
    
    /**
     * No marking future attendance — silently ignored rather than erroring, since the grid already dims/disables future cells.
     */
    public final void selectDate(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date) {
    }
    
    public final void goToPreviousMonth() {
    }
    
    /**
     * Can't navigate past the current month — there's nothing to mark there yet.
     */
    public final void goToNextMonth() {
    }
    
    /**
     * The full grid for the currently visible month: leading blanks + one cell per day, fully re-derived whenever any input changes.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.calendar.CalendarDayUi>> getCalendarDays() {
        return null;
    }
    
    /**
     * True when the selected date is a holiday — either explicitly marked, or (new) any Saturday/Sunday via [HolidayRules].
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isSelectedDateHoliday() {
        return null;
    }
    
    /**
     * Whether the holiday toggle button should be shown at all — hidden on a weekend, see [HolidayRules.isOverridable].
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isSelectedDateHolidayOverridable() {
        return null;
    }
    
    /**
     * Same shape as AttendanceViewModel.rows — just for the selected date instead of always "today".
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.attendance.AttendanceRowUi>> getRows() {
        return null;
    }
    
    public final void mark(long subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    public final void toggleSelectedDateHoliday() {
    }
}