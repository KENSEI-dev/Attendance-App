package com.attendance.app.ui.publish;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/attendance/app/ui/publish/PublishUiState;", "", "()V", "Done", "Failed", "Idle", "Publishing", "Lcom/attendance/app/ui/publish/PublishUiState$Done;", "Lcom/attendance/app/ui/publish/PublishUiState$Failed;", "Lcom/attendance/app/ui/publish/PublishUiState$Idle;", "Lcom/attendance/app/ui/publish/PublishUiState$Publishing;", "app_debug"})
public abstract class PublishUiState {
    
    private PublishUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/attendance/app/ui/publish/PublishUiState$Done;", "Lcom/attendance/app/ui/publish/PublishUiState;", "settings", "Lcom/attendance/app/data/publish/GitHubPublishSettings;", "outcome", "Lcom/attendance/app/data/publish/PublishOutcome;", "lastPublishedAt", "", "(Lcom/attendance/app/data/publish/GitHubPublishSettings;Lcom/attendance/app/data/publish/PublishOutcome;Ljava/lang/String;)V", "getLastPublishedAt", "()Ljava/lang/String;", "getOutcome", "()Lcom/attendance/app/data/publish/PublishOutcome;", "getSettings", "()Lcom/attendance/app/data/publish/GitHubPublishSettings;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Done extends com.attendance.app.ui.publish.PublishUiState {
        @org.jetbrains.annotations.NotNull()
        private final com.attendance.app.data.publish.GitHubPublishSettings settings = null;
        @org.jetbrains.annotations.NotNull()
        private final com.attendance.app.data.publish.PublishOutcome outcome = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String lastPublishedAt = null;
        
        public Done(@org.jetbrains.annotations.NotNull()
        com.attendance.app.data.publish.GitHubPublishSettings settings, @org.jetbrains.annotations.NotNull()
        com.attendance.app.data.publish.PublishOutcome outcome, @org.jetbrains.annotations.NotNull()
        java.lang.String lastPublishedAt) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.data.publish.GitHubPublishSettings getSettings() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.data.publish.PublishOutcome getOutcome() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLastPublishedAt() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.data.publish.GitHubPublishSettings component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.data.publish.PublishOutcome component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.ui.publish.PublishUiState.Done copy(@org.jetbrains.annotations.NotNull()
        com.attendance.app.data.publish.GitHubPublishSettings settings, @org.jetbrains.annotations.NotNull()
        com.attendance.app.data.publish.PublishOutcome outcome, @org.jetbrains.annotations.NotNull()
        java.lang.String lastPublishedAt) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001f\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/attendance/app/ui/publish/PublishUiState$Failed;", "Lcom/attendance/app/ui/publish/PublishUiState;", "settings", "Lcom/attendance/app/data/publish/GitHubPublishSettings;", "message", "", "(Lcom/attendance/app/data/publish/GitHubPublishSettings;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getSettings", "()Lcom/attendance/app/data/publish/GitHubPublishSettings;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Failed extends com.attendance.app.ui.publish.PublishUiState {
        @org.jetbrains.annotations.Nullable()
        private final com.attendance.app.data.publish.GitHubPublishSettings settings = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public Failed(@org.jetbrains.annotations.Nullable()
        com.attendance.app.data.publish.GitHubPublishSettings settings, @org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.attendance.app.data.publish.GitHubPublishSettings getSettings() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.attendance.app.data.publish.GitHubPublishSettings component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.ui.publish.PublishUiState.Failed copy(@org.jetbrains.annotations.Nullable()
        com.attendance.app.data.publish.GitHubPublishSettings settings, @org.jetbrains.annotations.NotNull()
        java.lang.String message) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/attendance/app/ui/publish/PublishUiState$Idle;", "Lcom/attendance/app/ui/publish/PublishUiState;", "settings", "Lcom/attendance/app/data/publish/GitHubPublishSettings;", "lastPublishedAt", "", "(Lcom/attendance/app/data/publish/GitHubPublishSettings;Ljava/lang/String;)V", "getLastPublishedAt", "()Ljava/lang/String;", "getSettings", "()Lcom/attendance/app/data/publish/GitHubPublishSettings;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Idle extends com.attendance.app.ui.publish.PublishUiState {
        @org.jetbrains.annotations.Nullable()
        private final com.attendance.app.data.publish.GitHubPublishSettings settings = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String lastPublishedAt = null;
        
        public Idle(@org.jetbrains.annotations.Nullable()
        com.attendance.app.data.publish.GitHubPublishSettings settings, @org.jetbrains.annotations.Nullable()
        java.lang.String lastPublishedAt) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.attendance.app.data.publish.GitHubPublishSettings getSettings() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getLastPublishedAt() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.attendance.app.data.publish.GitHubPublishSettings component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.ui.publish.PublishUiState.Idle copy(@org.jetbrains.annotations.Nullable()
        com.attendance.app.data.publish.GitHubPublishSettings settings, @org.jetbrains.annotations.Nullable()
        java.lang.String lastPublishedAt) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/attendance/app/ui/publish/PublishUiState$Publishing;", "Lcom/attendance/app/ui/publish/PublishUiState;", "()V", "app_debug"})
    public static final class Publishing extends com.attendance.app.ui.publish.PublishUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.attendance.app.ui.publish.PublishUiState.Publishing INSTANCE = null;
        
        private Publishing() {
        }
    }
}