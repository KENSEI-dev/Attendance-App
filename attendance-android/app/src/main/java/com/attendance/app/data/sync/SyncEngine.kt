package com.attendance.app.data.sync

import android.content.Context
import com.attendance.app.repository.AttendanceMergeOutcome
import com.attendance.app.repository.AttendanceRepository
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

data class SyncResult(
    val filesRead: Int,
    val conflictFilesFound: Int,
    val eventsProcessed: Int,
    val subjectsCreated: Int,
    val attendanceCreated: Int,
    val attendanceUpdated: Int,
    val errors: List<String>
) {
    /** Combined count for anything that changed the local DB, for callers that don't care about the split. */
    val attendanceMerged: Int get() = attendanceCreated + attendanceUpdated
}

/**
 * Reads every changes_*.jsonl file in the sync folder (all devices' logs,
 * including any *.sync-conflict-* copies Syncthing may have created) and
 * replays every event into the local Room DB.
 *
 * This is a full replay every time, not an incremental one — simpler and
 * safer to reason about for now. Replaying an event twice is harmless:
 * subject creation is upserted by name, and attendance uses the same
 * (subjectId, date, deviceId) unique index from Session 2, so re-inserting
 * an already-merged record is a no-op.
 *
 * Session 7: *.sync-conflict-* files are counted separately (see
 * [SyncResult.conflictFilesFound]) purely for visibility — repeated
 * conflicts usually mean two physical devices ended up sharing the same
 * device_id (e.g. a backup restore), which is worth the user noticing.
 * Their events are still merged like any other file; duplicate
 * (subjectId, date, deviceId) rows from them are resolved by recency in
 * [AttendanceRepository.mergeAttendanceEvent] rather than by file read
 * order, which used to silently let whichever file happened to be listed
 * first win.
 *
 * Known limitation still NOT solved here (out of scope for this session):
 * the SAME subject+date marked with a DIFFERENT status from two DIFFERENT
 * devices still keeps both rows — that dedup key includes device_id, so
 * they're legitimately different rows, not a conflict-file duplicate. The
 * app prefers the local device's own record when deciding what to show as
 * "today's status." Real cross-device conflict resolution is a bigger
 * design problem than fits in one session.
 */
class SyncEngine(
    private val context: Context,
    private val folderManager: SyncFolderManager,
    private val repository: AttendanceRepository
) {
    private val gson = Gson()

    suspend fun syncNow(): SyncResult {
        val files = folderManager.listChangeFiles()
        val conflictCount = files.count { SyncFolderManager.isConflictFileName(it.name) }
        var eventsProcessed = 0
        var subjectsCreated = 0
        var attendanceCreated = 0
        var attendanceUpdated = 0
        val errors = mutableListOf<String>()

        for (file in files) {
            try {
                context.contentResolver.openInputStream(file.uri)?.use { input ->
                    BufferedReader(InputStreamReader(input)).useLines { lines ->
                        for (line in lines) {
                            if (line.isBlank()) continue
                            val event = runCatching {
                                gson.fromJson(line, SyncEventJson::class.java).toSyncEvent()
                            }.getOrNull() ?: continue

                            eventsProcessed++
                            when (event) {
                                is SyncEvent.SubjectCreated -> {
                                    val created = repository.mergeSubjectEvent(event.name, event.code)
                                    if (created) subjectsCreated++
                                }
                                is SyncEvent.AttendanceMarked -> {
                                    when (repository.mergeAttendanceEvent(
                                        subjectName = event.subjectName,
                                        date = event.date,
                                        status = event.status,
                                        originDeviceId = event.deviceId,
                                        createdAt = event.createdAt
                                    )) {
                                        AttendanceMergeOutcome.CREATED -> attendanceCreated++
                                        AttendanceMergeOutcome.UPDATED -> attendanceUpdated++
                                        AttendanceMergeOutcome.UNCHANGED, AttendanceMergeOutcome.SKIPPED -> Unit
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                errors.add("${file.name}: ${e.message}")
            }
        }

        return SyncResult(
            filesRead = files.size,
            conflictFilesFound = conflictCount,
            eventsProcessed = eventsProcessed,
            subjectsCreated = subjectsCreated,
            attendanceCreated = attendanceCreated,
            attendanceUpdated = attendanceUpdated,
            errors = errors
        )
    }
}
