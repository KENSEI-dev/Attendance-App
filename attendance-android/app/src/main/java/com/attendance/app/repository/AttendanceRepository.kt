package com.attendance.app.repository

import com.attendance.app.data.AttendanceDao
import com.attendance.app.data.AttendanceEntity
import com.attendance.app.data.SubjectDao
import com.attendance.app.data.SubjectEntity
import com.attendance.app.data.SubjectSummary
import com.attendance.app.data.sync.SyncEvent
import com.attendance.app.data.sync.SyncLogWriter
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AttendanceRepository(
    private val subjectDao: SubjectDao,
    private val attendanceDao: AttendanceDao,
    private val deviceId: String,
    // Null until a sync folder is configured (see SyncFolderManager) — until
    // then every method below still works, it just doesn't log anything.
    private val syncLogWriter: SyncLogWriter? = null
) {
    private val timestampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private fun now(): String = timestampFormat.format(Date())

    // --- Subjects (local writes) ---

    fun getSubjects(): Flow<List<SubjectEntity>> = subjectDao.getAll()

    suspend fun addSubject(name: String, code: String?) {
        val cleanName = name.trim()
        val cleanCode = code?.trim()?.ifBlank { null }
        subjectDao.insert(SubjectEntity(name = cleanName, code = cleanCode, createdAt = now()))
        syncLogWriter?.append(SyncEvent.SubjectCreated(cleanName, cleanCode, deviceId, now()))
    }

    suspend fun deleteSubject(subject: SubjectEntity) {
        subjectDao.delete(subject)
        // Deliberately not logged to the sync log yet — deletions need their
        // own event type + tombstone handling to merge safely, which is out
        // of scope for this session. Deleting a subject is local-only for now.
    }

    // --- Attendance (local writes) ---

    fun getAttendanceForDate(date: String): Flow<List<AttendanceEntity>> =
        attendanceDao.getForDate(date)

    fun getSubjectSummaries(): Flow<List<SubjectSummary>> =
        attendanceDao.getSubjectSummaries()

    suspend fun markAttendance(subjectId: Long, date: String, status: String) {
        val timestamp = now()
        attendanceDao.insert(
            AttendanceEntity(
                subjectId = subjectId, date = date, status = status,
                deviceId = deviceId, createdAt = timestamp
            )
        )
        val subject = subjectDao.getById(subjectId)
        if (subject != null) {
            syncLogWriter?.append(
                SyncEvent.AttendanceMarked(subject.name, date, status, deviceId, timestamp)
            )
        }
    }

    suspend fun clearAttendance(subjectId: Long, date: String) {
        attendanceDao.deleteRecord(subjectId, date, deviceId)
    }

    // --- Merge (incoming events from other devices, via SyncEngine) ---
    // These never write back to the sync log — only genuinely local actions
    // above do that. Re-logging a merged event would create an infinite
    // sync loop across devices.

    /** Returns true if a new local subject row was created (false if it already existed). */
    suspend fun mergeSubjectEvent(name: String, code: String?): Boolean {
        val found = subjectDao.findByName(name)
        if (found != null) return false
        subjectDao.insert(SubjectEntity(name = name, code = code, createdAt = now()))
        return true
    }

    /**
     * Session 7: a Syncthing *.sync-conflict-*.jsonl file can carry a second,
     * divergent copy of the SAME (subjectId, date, deviceId) triple — e.g. an
     * old device_id got duplicated onto two physical devices via a restored
     * backup, and each independently marked today's attendance differently.
     * Previously this was a plain insert-or-ignore, so whichever file the
     * merge happened to read FIRST silently won — not necessarily the most
     * recent mark. Now: if a row for that key already exists, only overwrite
     * it when the incoming event's createdAt is actually newer (the
     * "yyyy-MM-dd HH:mm:ss" format sorts correctly as a plain string, no
     * parsing needed since every device writes it with the same format/locale).
     */
    suspend fun mergeAttendanceEvent(
        subjectName: String,
        date: String,
        status: String,
        originDeviceId: String,
        createdAt: String
    ): AttendanceMergeOutcome {
        var subject = subjectDao.findByName(subjectName)
        if (subject == null) {
            // Attendance event arrived before its subject's creation event
            // was merged (files are read in arbitrary order) — create a
            // stub subject now so the record isn't dropped.
            subjectDao.insert(SubjectEntity(name = subjectName, code = null, createdAt = now()))
            subject = subjectDao.findByName(subjectName)
        }
        if (subject == null) return AttendanceMergeOutcome.SKIPPED

        val existing = attendanceDao.findByKey(subject.id, date, originDeviceId)
        if (existing == null) {
            val rowId = attendanceDao.insert(
                AttendanceEntity(
                    subjectId = subject.id, date = date, status = status,
                    deviceId = originDeviceId, createdAt = createdAt
                )
            )
            return if (rowId != -1L) AttendanceMergeOutcome.CREATED else AttendanceMergeOutcome.SKIPPED
        }

        if (existing.status == status) return AttendanceMergeOutcome.UNCHANGED // identical replay, nothing to do

        return if (createdAt > existing.createdAt) {
            attendanceDao.update(existing.copy(status = status, createdAt = createdAt))
            AttendanceMergeOutcome.UPDATED
        } else {
            AttendanceMergeOutcome.UNCHANGED // incoming event is the same age or older — existing row wins
        }
    }
}

enum class AttendanceMergeOutcome { CREATED, UPDATED, UNCHANGED, SKIPPED }
