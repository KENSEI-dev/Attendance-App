package com.attendance.app.ui.publish;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J.\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001b"}, d2 = {"Lcom/attendance/app/ui/publish/PublishViewModel;", "Landroidx/lifecycle/ViewModel;", "settingsManager", "Lcom/attendance/app/data/publish/GitHubSettingsManager;", "exporter", "Lcom/attendance/app/data/publish/AttendanceJsonExporter;", "publisher", "Lcom/attendance/app/data/publish/GitHubPublisher;", "(Lcom/attendance/app/data/publish/GitHubSettingsManager;Lcom/attendance/app/data/publish/AttendanceJsonExporter;Lcom/attendance/app/data/publish/GitHubPublisher;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/attendance/app/ui/publish/PublishUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "currentSettings", "Lcom/attendance/app/data/publish/GitHubPublishSettings;", "publishNow", "", "saveSettings", "pat", "", "owner", "repo", "path", "branch", "app_debug"})
public final class PublishViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.publish.GitHubSettingsManager settingsManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.publish.AttendanceJsonExporter exporter = null;
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.publish.GitHubPublisher publisher = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.attendance.app.ui.publish.PublishUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.attendance.app.ui.publish.PublishUiState> state = null;
    
    public PublishViewModel(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.GitHubSettingsManager settingsManager, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.AttendanceJsonExporter exporter, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.GitHubPublisher publisher) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.attendance.app.ui.publish.PublishUiState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.attendance.app.data.publish.GitHubPublishSettings currentSettings() {
        return null;
    }
    
    public final void saveSettings(@org.jetbrains.annotations.NotNull()
    java.lang.String pat, @org.jetbrains.annotations.NotNull()
    java.lang.String owner, @org.jetbrains.annotations.NotNull()
    java.lang.String repo, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    java.lang.String branch) {
    }
    
    public final void publishNow() {
    }
}