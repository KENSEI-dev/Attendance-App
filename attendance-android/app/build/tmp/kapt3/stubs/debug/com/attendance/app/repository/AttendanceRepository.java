package com.attendance.app.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\tH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001cJ\"\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001e2\u0006\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\tJ\u001a\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u001f0\u001e2\u0006\u0010\u0017\u001a\u00020\tJ\u0018\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\u0017\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010&J\u0012\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0\u001eJ\u0012\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u001f0\u001eJ\u0012\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001f0\u001eJ&\u0010+\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010,\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010-J\"\u0010.\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\t2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\tH\u0086@\u00a2\u0006\u0002\u0010\u0013J6\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u00105J \u00106\u001a\u0002072\u0006\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\tH\u0086@\u00a2\u0006\u0002\u0010\u0013J\b\u00108\u001a\u00020\tH\u0002J\u0016\u00109\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006:"}, d2 = {"Lcom/attendance/app/repository/AttendanceRepository;", "", "subjectDao", "Lcom/attendance/app/data/SubjectDao;", "attendanceDao", "Lcom/attendance/app/data/AttendanceDao;", "holidayDao", "Lcom/attendance/app/data/HolidayDao;", "deviceId", "", "syncLogWriter", "Lcom/attendance/app/data/sync/SyncLogWriter;", "(Lcom/attendance/app/data/SubjectDao;Lcom/attendance/app/data/AttendanceDao;Lcom/attendance/app/data/HolidayDao;Ljava/lang/String;Lcom/attendance/app/data/sync/SyncLogWriter;)V", "timestampFormat", "Ljava/text/SimpleDateFormat;", "addSubject", "", "name", "code", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearAttendance", "subjectId", "", "date", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSubject", "subject", "Lcom/attendance/app/data/SubjectEntity;", "(Lcom/attendance/app/data/SubjectEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAttendanceBetween", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/attendance/app/data/AttendanceEntity;", "startDate", "endDate", "getAttendanceForDate", "getHoliday", "Lcom/attendance/app/data/HolidayEntity;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHolidays", "getSubjectSummaries", "Lcom/attendance/app/data/SubjectSummary;", "getSubjects", "markAttendance", "status", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markHoliday", "reason", "mergeAttendanceEvent", "Lcom/attendance/app/repository/AttendanceMergeOutcome;", "subjectName", "originDeviceId", "createdAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mergeSubjectEvent", "", "now", "unmarkHoliday", "app_debug"})
public final class AttendanceRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.SubjectDao subjectDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.AttendanceDao attendanceDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.HolidayDao holidayDao = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    @org.jetbrains.annotations.Nullable()
    private final com.attendance.app.data.sync.SyncLogWriter syncLogWriter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat timestampFormat = null;
    
    public AttendanceRepository(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.SubjectDao subjectDao, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.AttendanceDao attendanceDao, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.HolidayDao holidayDao, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.Nullable()
    com.attendance.app.data.sync.SyncLogWriter syncLogWriter) {
        super();
    }
    
    private final java.lang.String now() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.SubjectEntity>> getSubjects() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addSubject(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteSubject(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.SubjectEntity subject, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.AttendanceEntity>> getAttendanceForDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date) {
        return null;
    }
    
    /**
     * Whole-month range read, for the Calendar tab's day grid.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.AttendanceEntity>> getAttendanceBetween(@org.jetbrains.annotations.NotNull()
    java.lang.String startDate, @org.jetbrains.annotations.NotNull()
    java.lang.String endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.SubjectSummary>> getSubjectSummaries() {
        return null;
    }
    
    /**
     * Was a plain insert with OnConflictStrategy.IGNORE — the actual bug
     * behind "I can't change an already-marked status": tapping Present
     * then Absent for the same subject+date silently did nothing on the
     * second tap, because a row for that (subjectId, date, deviceId) key
     * already existed and IGNORE means exactly that, ignore. Now a real
     * upsert: update the existing row's status if one exists and differs,
     * insert fresh otherwise.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markAttendance(long subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAttendance(long subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.HolidayEntity>> getHolidays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getHoliday(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.attendance.app.data.HolidayEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markHoliday(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.Nullable()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unmarkHoliday(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Returns true if a new local subject row was created (false if it already existed).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object mergeSubjectEvent(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Session 7: a Syncthing *.sync-conflict-*.jsonl file can carry a second,
     * divergent copy of the SAME (subjectId, date, deviceId) triple — e.g. an
     * old device_id got duplicated onto two physical devices via a restored
     * backup, and each independently marked today's attendance differently.
     * Previously this was a plain insert-or-ignore, so whichever file the
     * merge happened to read FIRST silently won — not necessarily the most
     * recent mark. Now: if a row for that key already exists, only overwrite
     * it when the incoming event's createdAt is actually newer (the
     * "yyyy-MM-dd HH:mm:ss" format sorts correctly as a plain string, no
     * parsing needed since every device writes it with the same format/locale).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object mergeAttendanceEvent(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.lang.String originDeviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String createdAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.attendance.app.repository.AttendanceMergeOutcome> $completion) {
        return null;
    }
}