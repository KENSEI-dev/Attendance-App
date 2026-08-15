package com.attendance.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/attendance/app/data/AttendanceAggregation;", "", "()V", "isoDate", "Ljava/time/format/DateTimeFormatter;", "aggregateByDay", "", "Ljava/time/LocalDate;", "Lcom/attendance/app/data/DayStatus;", "records", "", "Lcom/attendance/app/data/AttendanceEntity;", "app_debug"})
public final class AttendanceAggregation {
    @org.jetbrains.annotations.NotNull()
    private static final java.time.format.DateTimeFormatter isoDate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.attendance.app.data.AttendanceAggregation INSTANCE = null;
    
    private AttendanceAggregation() {
        super();
    }
    
    /**
     * Priority when a day has mixed results across subjects: absent > present
     * > cancelled, so a day you missed anything on never gets hidden behind
     * a day you also attended something else.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.time.LocalDate, com.attendance.app.data.DayStatus> aggregateByDay(@org.jetbrains.annotations.NotNull()
    java.util.List<com.attendance.app.data.AttendanceEntity> records) {
        return null;
    }
}