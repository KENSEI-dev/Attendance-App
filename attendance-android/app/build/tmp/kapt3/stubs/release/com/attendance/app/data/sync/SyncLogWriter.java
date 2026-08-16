package com.attendance.app.data.sync;

/**
 * Appends locally-created events (a subject added, an attendance mark) to
 * this device's own log file inside the sync folder. Never writes to any
 * other device's file.
 *
 * If no sync folder has been configured yet, append() is a silent no-op —
 * the app still works fully offline/local-only; the user can set up sync
 * whenever they're ready from the Sync tab.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/attendance/app/data/sync/SyncLogWriter;", "", "context", "Landroid/content/Context;", "folderManager", "Lcom/attendance/app/data/sync/SyncFolderManager;", "deviceId", "", "(Landroid/content/Context;Lcom/attendance/app/data/sync/SyncFolderManager;Ljava/lang/String;)V", "gson", "Lcom/google/gson/Gson;", "append", "", "event", "Lcom/attendance/app/data/sync/SyncEvent;", "app_release"})
public final class SyncLogWriter {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.attendance.app.data.sync.SyncFolderManager folderManager = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    public SyncLogWriter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.attendance.app.data.sync.SyncFolderManager folderManager, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        super();
    }
    
    public final void append(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.sync.SyncEvent event) {
    }
}