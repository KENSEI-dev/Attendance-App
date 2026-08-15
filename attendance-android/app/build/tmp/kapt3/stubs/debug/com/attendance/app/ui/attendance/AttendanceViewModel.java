package com.attendance.app.ui.attendance;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2 = {"Lcom/attendance/app/ui/attendance/AttendanceViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/attendance/app/repository/AttendanceRepository;", "localDeviceId", "", "(Lcom/attendance/app/repository/AttendanceRepository;Ljava/lang/String;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "rows", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/attendance/app/ui/attendance/AttendanceRowUi;", "getRows", "()Lkotlinx/coroutines/flow/StateFlow;", "today", "getToday", "()Ljava/lang/String;", "clear", "", "subjectId", "", "mark", "status", "app_debug"})
public final class AttendanceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.repository.AttendanceRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localDeviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String today = null;
    
    /**
     * One row per subject: today's marked status (if any) + overall attendance %.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.attendance.AttendanceRowUi>> rows = null;
    
    public AttendanceViewModel(@org.jetbrains.annotations.NotNull()
    com.attendance.app.repository.AttendanceRepository repository, @org.jetbrains.annotations.NotNull()
    java.lang.String localDeviceId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToday() {
        return null;
    }
    
    /**
     * One row per subject: today's marked status (if any) + overall attendance %.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.attendance.app.ui.attendance.AttendanceRowUi>> getRows() {
        return null;
    }
    
    public final void mark(long subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    public final void clear(long subjectId) {
    }
}