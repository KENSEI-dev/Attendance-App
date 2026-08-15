package com.attendance.app.widget

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WidgetRefreshScheduler {

    // 15 minutes is WorkManager's enforced minimum for periodic work — the
    // system will not run it more often than this regardless of what's set here.
    private const val INTERVAL_MINUTES = 15L

    fun schedulePeriodic(context: Context) {
        val request = PeriodicWorkRequestBuilder<WidgetRefreshWorker>(INTERVAL_MINUTES, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WidgetRefreshWorker.WORK_NAME,
            // KEEP: if this is already scheduled (e.g. app was updated, or a
            // second widget was added), don't reset the existing schedule.
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancelPeriodic(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WidgetRefreshWorker.WORK_NAME)
    }
}
