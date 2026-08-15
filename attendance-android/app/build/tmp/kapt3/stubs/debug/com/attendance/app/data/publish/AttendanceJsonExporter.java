package com.attendance.app.data.publish;

/**
 * Builds the attendance.json payload from whatever's currently in the local
 * Room DB. No date-range cap here (unlike the widget's ~20-week lookback) —
 * this runs once when the user taps "Publish", not on a timer, so exporting
 * full history is cheap enough and gives the future website a complete
 * picture rather than a rolling window.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000bJ\f\u0010\r\u001a\u00020\u0005*\u00020\u000fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/attendance/app/data/publish/AttendanceJsonExporter;", "", "db", "Lcom/attendance/app/data/AppDatabase;", "deviceId", "", "(Lcom/attendance/app/data/AppDatabase;Ljava/lang/String;)V", "gson", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "buildPayload", "Lcom/attendance/app/data/publish/AttendancePayload;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toJsonString", "payload", "Lcom/attendance/app/data/DayStatus;", "app_debug"})
public final class AttendanceJsonExporter {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    private final com.google.gson.Gson gson = null;
    
    public AttendanceJsonExporter(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.AppDatabase db, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object buildPayload(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.attendance.app.data.publish.AttendancePayload> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String toJsonString(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.AttendancePayload payload) {
        return null;
    }
    
    private final java.lang.String toJsonString(com.attendance.app.data.DayStatus $this$toJsonString) {
        return null;
    }
}