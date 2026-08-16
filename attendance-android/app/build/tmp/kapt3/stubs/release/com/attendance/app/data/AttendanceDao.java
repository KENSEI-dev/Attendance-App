package com.attendance.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J&\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\tJ(\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ$\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u00102\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0007H\'J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u00102\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u00102\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u000e\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010\u0018\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u0014\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\r0\u0010H\'J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u001f\u00a8\u0006!"}, d2 = {"Lcom/attendance/app/data/AttendanceDao;", "", "deleteRecord", "", "subjectId", "", "date", "", "deviceId", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findByKey", "Lcom/attendance/app/data/AttendanceEntity;", "getAllSnapshot", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBetween", "Lkotlinx/coroutines/flow/Flow;", "startDate", "endDate", "getForDate", "getForSubject", "getOverallCounts", "Lcom/attendance/app/data/OverallCounts;", "getSince", "sinceDate", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSubjectSummaries", "Lcom/attendance/app/data/SubjectSummary;", "getSubjectSummariesSnapshot", "insert", "record", "(Lcom/attendance/app/data/AttendanceEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_release"})
@androidx.room.Dao()
public abstract interface AttendanceDao {
    
    /**
     * IGNORE on conflict mirrors the Python seed script's
     * "INSERT OR IGNORE" against the (subjectId, date, deviceId)
     * unique index — marking the same subject/date twice from the
     * same device is a no-op, not a crash.
     */
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.AttendanceEntity record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Session 7: needed to resolve duplicates that arrive via a Syncthing
     * *.sync-conflict-*.jsonl file — same (subjectId, date, deviceId) triple,
     * but possibly a different status, both claiming to be that device's own
     * record. Look up the existing row so the repository can decide whether
     * the incoming event is actually newer before touching it.
     */
    @androidx.room.Query(value = "SELECT * FROM attendance WHERE subjectId = :subjectId AND date = :date AND deviceId = :deviceId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findByKey(long subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.attendance.app.data.AttendanceEntity> $completion);
    
    /**
     * Room matches by primary key — pass the existing row's id when overwriting it with newer data.
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.AttendanceEntity record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM attendance WHERE date = :date ORDER BY subjectId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.AttendanceEntity>> getForDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date);
    
    /**
     * Session: calendar view needs a whole visible month's worth of records at once, for coloring the day grid.
     */
    @androidx.room.Query(value = "SELECT * FROM attendance WHERE date BETWEEN :startDate AND :endDate")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.AttendanceEntity>> getBetween(@org.jetbrains.annotations.NotNull()
    java.lang.String startDate, @org.jetbrains.annotations.NotNull()
    java.lang.String endDate);
    
    @androidx.room.Query(value = "SELECT * FROM attendance WHERE subjectId = :subjectId ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.AttendanceEntity>> getForSubject(long subjectId);
    
    @androidx.room.Query(value = "\n        SELECT s.id AS subjectId, s.name AS name, s.code AS code,\n               SUM(CASE WHEN a.status = \'present\' THEN 1 ELSE 0 END) AS present,\n               SUM(CASE WHEN a.status = \'absent\' THEN 1 ELSE 0 END) AS absent,\n               SUM(CASE WHEN a.status = \'cancelled\' THEN 1 ELSE 0 END) AS cancelled\n        FROM subjects s\n        LEFT JOIN attendance a ON a.subjectId = s.id\n        GROUP BY s.id\n        ORDER BY s.name ASC\n        ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.attendance.app.data.SubjectSummary>> getSubjectSummaries();
    
    @androidx.room.Query(value = "DELETE FROM attendance WHERE subjectId = :subjectId AND date = :date AND deviceId = :deviceId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteRecord(long subjectId, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM attendance WHERE date >= :sinceDate ORDER BY date ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSince(@org.jetbrains.annotations.NotNull()
    java.lang.String sinceDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.attendance.app.data.AttendanceEntity>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT SUM(CASE WHEN status = \'present\' THEN 1 ELSE 0 END) AS present,\n               SUM(CASE WHEN status = \'absent\' THEN 1 ELSE 0 END) AS absent\n        FROM attendance\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOverallCounts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.attendance.app.data.OverallCounts> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM attendance ORDER BY date ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllSnapshot(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.attendance.app.data.AttendanceEntity>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT s.id AS subjectId, s.name AS name, s.code AS code,\n               SUM(CASE WHEN a.status = \'present\' THEN 1 ELSE 0 END) AS present,\n               SUM(CASE WHEN a.status = \'absent\' THEN 1 ELSE 0 END) AS absent,\n               SUM(CASE WHEN a.status = \'cancelled\' THEN 1 ELSE 0 END) AS cancelled\n        FROM subjects s\n        LEFT JOIN attendance a ON a.subjectId = s.id\n        GROUP BY s.id\n        ORDER BY s.name ASC\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSubjectSummariesSnapshot(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.attendance.app.data.SubjectSummary>> $completion);
}