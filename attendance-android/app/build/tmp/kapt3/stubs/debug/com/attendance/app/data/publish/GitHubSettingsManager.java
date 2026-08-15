package com.attendance.app.data.publish;

/**
 * Stores the fine-grained PAT and repo target in EncryptedSharedPreferences
 * rather than plain SharedPreferences — this file otherwise sits in the
 * app's private storage as an unencrypted XML file, which is a bigger deal
 * to get wrong for a credential than for e.g. the device_id string
 * elsewhere in this app. Everything else here (owner/repo/path/branch)
 * isn't sensitive, but it's simplest to keep the whole settings blob in one
 * encrypted file rather than splitting it across two SharedPreferences.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\t\u001a\u00020\nJ\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u000eJ\u000e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/attendance/app/data/publish/GitHubSettingsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "masterKey", "Landroidx/security/crypto/MasterKey;", "prefs", "Landroid/content/SharedPreferences;", "clearSettings", "", "getLastPublishedAt", "", "getSettings", "Lcom/attendance/app/data/publish/GitHubPublishSettings;", "saveSettings", "settings", "setLastPublishedAt", "isoTimestamp", "Companion", "app_debug"})
public final class GitHubSettingsManager {
    @org.jetbrains.annotations.NotNull()
    private final androidx.security.crypto.MasterKey masterKey = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "attendance_github_publish_secure";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PAT = "pat";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_OWNER = "owner";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_REPO = "repo";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PATH = "path";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_BRANCH = "branch";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_PUBLISHED_AT = "last_published_at";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_PATH = "attendance.json";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_BRANCH = "main";
    @org.jetbrains.annotations.NotNull()
    public static final com.attendance.app.data.publish.GitHubSettingsManager.Companion Companion = null;
    
    public GitHubSettingsManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.attendance.app.data.publish.GitHubPublishSettings getSettings() {
        return null;
    }
    
    public final void saveSettings(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.GitHubPublishSettings settings) {
    }
    
    public final void clearSettings() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLastPublishedAt() {
        return null;
    }
    
    public final void setLastPublishedAt(@org.jetbrains.annotations.NotNull()
    java.lang.String isoTimestamp) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/attendance/app/data/publish/GitHubSettingsManager$Companion;", "", "()V", "DEFAULT_BRANCH", "", "DEFAULT_PATH", "KEY_BRANCH", "KEY_LAST_PUBLISHED_AT", "KEY_OWNER", "KEY_PAT", "KEY_PATH", "KEY_REPO", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}