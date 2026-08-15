package com.attendance.app.data.publish;

/**
 * Talks to the GitHub REST "Contents API" to create or update a single file
 * in a repo. https://docs.github.com/en/rest/repos/contents
 *
 * Always does a fresh GET immediately before the PUT to fetch the file's
 * current sha, rather than caching the sha from a previous publish — if the
 * file was ever edited outside this app (or from a second device), a stale
 * cached sha would make the PUT fail with a 409 conflict. The GET-then-PUT
 * is not atomic (a very unlucky race with another writer is technically
 * possible), but for one person publishing their own attendance from their
 * own devices, that's an acceptable risk for a big simplicity win.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0002J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\bH\u0002J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/attendance/app/data/publish/GitHubPublisher;", "", "()V", "client", "Lokhttp3/OkHttpClient;", "jsonMediaType", "Lokhttp3/MediaType;", "buildContentsUrl", "", "settings", "Lcom/attendance/app/data/publish/GitHubPublishSettings;", "describeError", "code", "", "bodyString", "fetchExistingSha", "contentsUrl", "publish", "Lcom/attendance/app/data/publish/PublishOutcome;", "jsonContent", "(Lcom/attendance/app/data/publish/GitHubPublishSettings;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GitHubPublisher {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.MediaType jsonMediaType = null;
    
    public GitHubPublisher() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object publish(@org.jetbrains.annotations.NotNull()
    com.attendance.app.data.publish.GitHubPublishSettings settings, @org.jetbrains.annotations.NotNull()
    java.lang.String jsonContent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.attendance.app.data.publish.PublishOutcome> $completion) {
        return null;
    }
    
    /**
     * Returns the file's current sha, or null if it doesn't exist yet (first push).
     */
    private final java.lang.String fetchExistingSha(com.attendance.app.data.publish.GitHubPublishSettings settings, java.lang.String contentsUrl) {
        return null;
    }
    
    private final java.lang.String buildContentsUrl(com.attendance.app.data.publish.GitHubPublishSettings settings) {
        return null;
    }
    
    private final java.lang.String describeError(int code, java.lang.String bodyString) {
        return null;
    }
}