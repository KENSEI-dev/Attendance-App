package com.attendance.app.data.sync;

/**
 * One line of a changes_<deviceId>.jsonl file = one SyncEvent, serialized as JSON.
 *
 * IMPORTANT: subjects are identified by NAME here, not by local Room id.
 * Room's subjectId is auto-increment per-database, so the same subject can
 * have a different numeric id on every device. Name is the only thing that's
 * consistent across devices, so it's the join key during merge. (Subject
 * "code", e.g. "PCCCS403", is carried along too but name is authoritative.)
 *
 * Each device writes ONLY to its own file (changes_<its-own-device-id>.jsonl),
 * so there's never a two-writer race on the same file — Syncthing just has to
 * replicate each device's file to the others. This sidesteps most sync
 * conflicts entirely.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\t\nB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006\u0082\u0001\u0002\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/attendance/app/data/sync/SyncEvent;", "", "()V", "createdAt", "", "getCreatedAt", "()Ljava/lang/String;", "deviceId", "getDeviceId", "AttendanceMarked", "SubjectCreated", "Lcom/attendance/app/data/sync/SyncEvent$AttendanceMarked;", "Lcom/attendance/app/data/sync/SyncEvent$SubjectCreated;", "app_release"})
public abstract class SyncEvent {
    
    private SyncEvent() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getDeviceId();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getCreatedAt();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\u0007\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u001c"}, d2 = {"Lcom/attendance/app/data/sync/SyncEvent$AttendanceMarked;", "Lcom/attendance/app/data/sync/SyncEvent;", "subjectName", "", "date", "status", "deviceId", "createdAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCreatedAt", "()Ljava/lang/String;", "getDate", "getDeviceId", "getStatus", "getSubjectName", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_release"})
    public static final class AttendanceMarked extends com.attendance.app.data.sync.SyncEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String subjectName = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String date = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String status = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String deviceId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String createdAt = null;
        
        public AttendanceMarked(@org.jetbrains.annotations.NotNull()
        java.lang.String subjectName, @org.jetbrains.annotations.NotNull()
        java.lang.String date, @org.jetbrains.annotations.NotNull()
        java.lang.String status, @org.jetbrains.annotations.NotNull()
        java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
        java.lang.String createdAt) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSubjectName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getStatus() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getDeviceId() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getCreatedAt() {
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
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.data.sync.SyncEvent.AttendanceMarked copy(@org.jetbrains.annotations.NotNull()
        java.lang.String subjectName, @org.jetbrains.annotations.NotNull()
        java.lang.String date, @org.jetbrains.annotations.NotNull()
        java.lang.String status, @org.jetbrains.annotations.NotNull()
        java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
        java.lang.String createdAt) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J3\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0006\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\u00a8\u0006\u0019"}, d2 = {"Lcom/attendance/app/data/sync/SyncEvent$SubjectCreated;", "Lcom/attendance/app/data/sync/SyncEvent;", "name", "", "code", "deviceId", "createdAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getCreatedAt", "getDeviceId", "getName", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_release"})
    public static final class SubjectCreated extends com.attendance.app.data.sync.SyncEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String code = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String deviceId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String createdAt = null;
        
        public SubjectCreated(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.Nullable()
        java.lang.String code, @org.jetbrains.annotations.NotNull()
        java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
        java.lang.String createdAt) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getCode() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getDeviceId() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getCreatedAt() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.data.sync.SyncEvent.SubjectCreated copy(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.Nullable()
        java.lang.String code, @org.jetbrains.annotations.NotNull()
        java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
        java.lang.String createdAt) {
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
}