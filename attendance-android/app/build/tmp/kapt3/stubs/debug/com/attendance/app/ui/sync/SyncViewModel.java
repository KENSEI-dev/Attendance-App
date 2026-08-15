package com.attendance.app.ui.sync;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\u000bH\u0002J\u0006\u0010\u0016\u001a\u00020\u0014J\u0006\u0010\u0017\u001a\u00020\u0014J\u0006\u0010\u0018\u001a\u00020\u0014R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/attendance/app/ui/sync/SyncViewModel;", "Landroidx/lifecycle/ViewModel;", "folderManager", "Lcom/attendance/app/data/sync/SyncFolderManager;", "syncEngine", "Lcom/attendance/app/data/sync/SyncEngine;", "deviceId", "", "(Lcom/attendance/app/data/sync/SyncFolderManager;Lcom/attendance/app/data/sync/SyncEngine;Ljava/lang/String;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/attendance/app/ui/sync/SyncUiState;", "deviceIdLabel", "getDeviceIdLabel", "()Ljava/lang/String;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "archiveConflicts", "", "currentReadyOrEmptyState", "forgetFolder", "onFolderChosen", "syncNow", "app_debug"})
public final class SyncViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.sync.SyncFolderManager folderManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.sync.SyncEngine syncEngine = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.attendance.app.ui.sync.SyncUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.attendance.app.ui.sync.SyncUiState> state = null;
    
    public SyncViewModel(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.sync.SyncFolderManager folderManager, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.sync.SyncEngine syncEngine, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceIdLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.attendance.app.ui.sync.SyncUiState> getState() {
        return null;
    }
    
    private final com.attendance.app.ui.sync.SyncUiState currentReadyOrEmptyState() {
        return null;
    }
    
    public final void onFolderChosen() {
    }
    
    public final void forgetFolder() {
    }
    
    public final void syncNow() {
    }
    
    /**
     * Session 7: renames already-merged *.sync-conflict-*.jsonl files out of
     * the way, then re-syncs so the displayed summary reflects the cleaned-up
     * folder (conflictFilesFound should read 0 afterward).
     */
    public final void archiveConflicts() {
    }
}