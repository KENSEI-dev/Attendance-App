package com.attendance.app.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.attendance.app.data.AppDatabase
import com.attendance.app.data.publish.AttendanceJsonExporter
import com.attendance.app.data.publish.GitHubPublisher
import com.attendance.app.data.publish.GitHubSettingsManager
import com.attendance.app.data.sync.SyncEngine
import com.attendance.app.data.sync.SyncFolderManager
import com.attendance.app.data.sync.SyncLogWriter
import com.attendance.app.repository.AttendanceRepository
import com.attendance.app.ui.attendance.AttendanceViewModel
import com.attendance.app.ui.publish.PublishViewModel
import com.attendance.app.ui.subjects.SubjectsViewModel
import com.attendance.app.ui.sync.SyncViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val deviceId: String = DeviceIdProvider.getOrCreate(context)
    private val folderManager = SyncFolderManager(context)
    private val syncLogWriter = SyncLogWriter(context, folderManager, deviceId)

    private val repository: AttendanceRepository
    private val db: AppDatabase = AppDatabase.getInstance(context)

    init {
        repository = AttendanceRepository(db.subjectDao(), db.attendanceDao(), deviceId, syncLogWriter)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SubjectsViewModel::class.java) ->
                SubjectsViewModel(repository) as T

            modelClass.isAssignableFrom(AttendanceViewModel::class.java) ->
                AttendanceViewModel(repository, deviceId) as T

            modelClass.isAssignableFrom(SyncViewModel::class.java) -> {
                val syncEngine = SyncEngine(context, folderManager, repository)
                SyncViewModel(folderManager, syncEngine, deviceId) as T
            }

            modelClass.isAssignableFrom(PublishViewModel::class.java) -> {
                val settingsManager = GitHubSettingsManager(context)
                val exporter = AttendanceJsonExporter(db, deviceId)
                PublishViewModel(settingsManager, exporter, GitHubPublisher()) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
