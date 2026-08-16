package com.attendance.app.ui.attendance;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u0005H\u0002J\u0016\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u0005J\u0006\u0010\u001d\u001a\u00020\u0017J\u0006\u0010\u001e\u001a\u00020\u0017R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\fR\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/attendance/app/ui/attendance/AttendanceViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/attendance/app/repository/AttendanceRepository;", "localDeviceId", "", "(Lcom/attendance/app/repository/AttendanceRepository;Ljava/lang/String;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "isTodayHoliday", "Lkotlinx/coroutines/flow/StateFlow;", "", "()Lkotlinx/coroutines/flow/StateFlow;", "isTodayHolidayOverridable", "rows", "", "Lcom/attendance/app/ui/attendance/AttendanceRowUi;", "getRows", "today", "getToday", "todayFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "clear", "", "subjectId", "", "computeToday", "mark", "status", "refreshToday", "toggleTodayHoliday", "app_release"})
public final class AttendanceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.repository.AttendanceRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localDeviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    
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
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> todayFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> today = null;
    
    /**
     * One row per subject: today's marked status (if any) + overall attendance %.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.attendance.AttendanceRowUi>> rows = null;
    
    /**
     * True when [today] is a holiday — either explicitly marked, or (new) any Saturday/Sunday via [HolidayRules].
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isTodayHoliday = null;
    
    /**
     * Whether the holiday toggle button should be shown at all — hidden on a weekend, see [HolidayRules.isOverridable].
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isTodayHolidayOverridable = null;
    
    public AttendanceViewModel(@org.jetbrains.annotations.NotNull()
    com.attendance.app.repository.AttendanceRepository repository, @org.jetbrains.annotations.NotNull()
    java.lang.String localDeviceId) {
        super();
    }
    
    private final java.lang.String computeToday() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getToday() {
        return null;
    }
    
    /**
     * Re-checks the system date and updates [today] if a day has actually passed. Safe to call often — no-op if unchanged.
     */
    public final void refreshToday() {
    }
    
    /**
     * One row per subject: today's marked status (if any) + overall attendance %.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.attendance.AttendanceRowUi>> getRows() {
        return null;
    }
    
    /**
     * True when [today] is a holiday — either explicitly marked, or (new) any Saturday/Sunday via [HolidayRules].
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isTodayHoliday() {
        return null;
    }
    
    /**
     * Whether the holiday toggle button should be shown at all — hidden on a weekend, see [HolidayRules.isOverridable].
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isTodayHolidayOverridable() {
        return null;
    }
    
    public final void toggleTodayHoliday() {
    }
    
    public final void mark(long subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    public final void clear(long subjectId) {
    }
}