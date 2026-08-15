package com.attendance.app.widget;

/**
 * All the widget-update logic lives here (not in AttendanceWidgetProvider)
 * so the periodic WorkManager worker can call the exact same code path —
 * there's no AppWidgetProvider instance available from inside a Worker.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\u0012\u001a\u00020\u000fH\u0002J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0015J&\u0010\u0016\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/attendance/app/widget/WidgetUpdater;", "", "()V", "LOOKBACK_DAYS", "", "timeFormatter", "Ljava/time/format/DateTimeFormatter;", "kotlin.jvm.PlatformType", "buildContentViews", "Landroid/widget/RemoteViews;", "context", "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetId", "", "(Landroid/content/Context;Landroid/appwidget/AppWidgetManager;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildErrorViews", "pendingIntentFlags", "updateAllWidgets", "", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateWidget", "app_debug"})
public final class WidgetUpdater {
    private static final long LOOKBACK_DAYS = 140L;
    private static final java.time.format.DateTimeFormatter timeFormatter = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.attendance.app.widget.WidgetUpdater INSTANCE = null;
    
    private WidgetUpdater() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateAllWidgets(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Session 7: the whole body used to run with no error handling — a
     * corrupted DB read or a bad widget-width value from the launcher would
     * throw inside a bare `CoroutineScope(Dispatchers.IO).launch {}` in the
     * provider, which has no exception handler and would crash the process,
     * not just the widget. Now any failure here falls back to a plain
     * "couldn't load" widget instead of taking the app down.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateWidget(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object buildContentViews(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, kotlin.coroutines.Continuation<? super android.widget.RemoteViews> $completion) {
        return null;
    }
    
    /**
     * Minimal fallback: a fresh RemoteViews inflate of the same layout, just
     * without touching the matrix ImageView — leaving it at whatever the XML
     * layout itself defaults to, rather than passing a null Bitmap into
     * setImageViewBitmap (which some RemoteViews/launcher combos handle
     * inconsistently).
     */
    private final android.widget.RemoteViews buildErrorViews(android.content.Context context) {
        return null;
    }
    
    private final int pendingIntentFlags() {
        return 0;
    }
}