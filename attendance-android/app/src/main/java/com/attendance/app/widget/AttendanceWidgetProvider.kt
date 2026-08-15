package com.attendance.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttendanceWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                for (id in appWidgetIds) {
                    WidgetUpdater.updateWidget(context.applicationContext, appWidgetManager, id)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle
    ) {
        // Widget was resized — redraw the matrix at the new width so the
        // week count fits, instead of waiting for the next scheduled refresh.
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                WidgetUpdater.updateWidget(context.applicationContext, appWidgetManager, appWidgetId)
            } finally {
                pendingResult.finish()
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_MANUAL_REFRESH) {
            val appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                val pendingResult = goAsync()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        WidgetUpdater.updateWidget(
                            context.applicationContext,
                            AppWidgetManager.getInstance(context),
                            appWidgetId
                        )
                    } finally {
                        pendingResult.finish()
                    }
                }
            }
        }
    }

    override fun onEnabled(context: Context) {
        // First widget instance was just placed — start the periodic refresh.
        WidgetRefreshScheduler.schedulePeriodic(context)
    }

    override fun onDisabled(context: Context) {
        // Last widget instance was just removed — no point refreshing data
        // nobody's looking at.
        WidgetRefreshScheduler.cancelPeriodic(context)
    }

    companion object {
        const val ACTION_MANUAL_REFRESH = "com.attendance.app.widget.ACTION_MANUAL_REFRESH"
    }
}
