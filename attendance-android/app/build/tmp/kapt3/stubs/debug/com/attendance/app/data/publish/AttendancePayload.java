package com.attendance.app.data.publish;

/**
 * Shape of the attendance.json file pushed to GitHub. The Session 6 Vercel
 * dashboard fetches this file raw from raw.githubusercontent.com and is
 * expected to render its own dot-matrix + summary from exactly this shape —
 * keep this in sync with whatever Session 6 ends up expecting.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH\u00c6\u0003JG\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e\u00a8\u0006!"}, d2 = {"Lcom/attendance/app/data/publish/AttendancePayload;", "", "generatedAt", "", "deviceId", "overall", "Lcom/attendance/app/data/publish/OverallPayload;", "subjects", "", "Lcom/attendance/app/data/publish/SubjectPayload;", "days", "Lcom/attendance/app/data/publish/DayPayload;", "(Ljava/lang/String;Ljava/lang/String;Lcom/attendance/app/data/publish/OverallPayload;Ljava/util/List;Ljava/util/List;)V", "getDays", "()Ljava/util/List;", "getDeviceId", "()Ljava/lang/String;", "getGeneratedAt", "getOverall", "()Lcom/attendance/app/data/publish/OverallPayload;", "getSubjects", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class AttendancePayload {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String generatedAt = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.publish.OverallPayload overall = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.attendance.app.data.publish.SubjectPayload> subjects = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.attendance.app.data.publish.DayPayload> days = null;
    
    public AttendancePayload(@org.jetbrains.annotations.NotNull()
    java.lang.String generatedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.OverallPayload overall, @org.jetbrains.annotations.NotNull()
    java.util.List<com.attendance.app.data.publish.SubjectPayload> subjects, @org.jetbrains.annotations.NotNull()
    java.util.List<com.attendance.app.data.publish.DayPayload> days) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGeneratedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.attendance.app.data.publish.OverallPayload getOverall() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.attendance.app.data.publish.SubjectPayload> getSubjects() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.attendance.app.data.publish.DayPayload> getDays() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.attendance.app.data.publish.OverallPayload component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.attendance.app.data.publish.SubjectPayload> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.attendance.app.data.publish.DayPayload> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.attendance.app.data.publish.AttendancePayload copy(@org.jetbrains.annotations.NotNull()
    java.lang.String generatedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.OverallPayload overall, @org.jetbrains.annotations.NotNull()
    java.util.List<com.attendance.app.data.publish.SubjectPayload> subjects, @org.jetbrains.annotations.NotNull()
    java.util.List<com.attendance.app.data.publish.DayPayload> days) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}