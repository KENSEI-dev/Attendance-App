package com.attendance.app.widget

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WidgetRefreshWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            WidgetUpdater.updateAllWidgets(applicationContext)
            Result.success()
        } catch (e: Exception) {
            // Transient failure (e.g. DB briefly locked mid-sync) — let
            // WorkManager retry with backoff rather than silently giving up.
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "attendance_widget_refresh"
    }
}
