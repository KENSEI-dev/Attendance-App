package com.attendance.app.data.sync;

/**
 * Manages the "sync folder" — a folder the user points the app at, expected
 * to be the same folder Syncthing is keeping in sync across their devices.
 *
 * Android's scoped storage means the app can't just read/write an arbitrary
 * path; the user has to grant access once via the system folder picker
 * (Storage Access Framework), and we persist that permission so it survives
 * app restarts and device reboots.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010\f\u001a\u0004\u0018\u00010\rJ\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u0010\u0010\u0012\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0013\u001a\u00020\u000fJ\u0006\u0010\u0014\u001a\u00020\u0015J\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u0017J\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\u0017J\u000e\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/attendance/app/data/sync/SyncFolderManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "archiveConflictFiles", "Lcom/attendance/app/data/sync/ArchiveResult;", "clearFolderChoice", "", "getFolder", "Landroidx/documentfile/provider/DocumentFile;", "getFolderDisplayName", "", "getFolderUri", "Landroid/net/Uri;", "getOrCreateOwnLogFile", "deviceId", "hasFolderConfigured", "", "listChangeFiles", "", "listConflictFiles", "persistFolderChoice", "treeUri", "Companion", "app_release"})
public final class SyncFolderManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FOLDER_URI = "sync_folder_uri";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CONFLICT_MARKER = ".sync-conflict-";
    @org.jetbrains.annotations.NotNull()
    public static final com.attendance.app.data.sync.SyncFolderManager.Companion Companion = null;
    
    public SyncFolderManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean hasFolderConfigured() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.net.Uri getFolderUri() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFolderDisplayName() {
        return null;
    }
    
    /**
     * Call this from an Activity/Fragment's ActivityResultLauncher callback.
     */
    public final void persistFolderChoice(@org.jetbrains.annotations.NotNull()
    android.net.Uri treeUri) {
    }
    
    public final void clearFolderChoice() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.documentfile.provider.DocumentFile getFolder() {
        return null;
    }
    
    /**
     * Returns all changes_*.jsonl files currently in the folder, including any sync-conflict copies.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<androidx.documentfile.provider.DocumentFile> listChangeFiles() {
        return null;
    }
    
    /**
     * Just the subset of [listChangeFiles] that Syncthing marked as a conflict copy.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<androidx.documentfile.provider.DocumentFile> listConflictFiles() {
        return null;
    }
    
    /**
     * Finds or creates this device's own log file: changes_<deviceId>.jsonl
     */
    @org.jetbrains.annotations.Nullable()
    public final androidx.documentfile.provider.DocumentFile getOrCreateOwnLogFile(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        return null;
    }
    
    /**
     * Session 7: renames every current conflict file to an "archived_" prefix
     * instead of deleting it. Its events are already merged into every
     * device's DB by this point — nothing is lost either way — but renaming
     * (a) stops it matching the "changes_" prefix so it's excluded from
     * future scans (no more re-parsing it forever), and (b) keeps the file
     * itself around as an audit trail in case something ever looks wrong,
     * rather than an irreversible delete that also has to propagate through
     * Syncthing to every other device.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.attendance.app.data.sync.ArchiveResult archiveConflictFiles() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/attendance/app/data/sync/SyncFolderManager$Companion;", "", "()V", "CONFLICT_MARKER", "", "KEY_FOLDER_URI", "isConflictFileName", "", "name", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Matches Syncthing's own conflict-copy naming: name.sync-conflict-YYYYMMDD-HHMMSS-XXXXXXX.ext
         */
        public final boolean isConflictFileName(@org.jetbrains.annotations.Nullable()
        java.lang.String name) {
            return false;
        }
    }
}