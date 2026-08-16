package com.attendance.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    /**
     * IGNORE on conflict mirrors the Python seed script's
     * "INSERT OR IGNORE" against the (subjectId, date, deviceId)
     * unique index — marking the same subject/date twice from the
     * same device is a no-op, not a crash.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: AttendanceEntity): Long

    /**
     * Session 7: needed to resolve duplicates that arrive via a Syncthing
     * *.sync-conflict-*.jsonl file — same (subjectId, date, deviceId) triple,
     * but possibly a different status, both claiming to be that device's own
     * record. Look up the existing row so the repository can decide whether
     * the incoming event is actually newer before touching it.
     */
    @Query("SELECT * FROM attendance WHERE subjectId = :subjectId AND date = :date AND deviceId = :deviceId LIMIT 1")
    suspend fun findByKey(subjectId: Long, date: String, deviceId: String): AttendanceEntity?

    /** Room matches by primary key — pass the existing row's id when overwriting it with newer data. */
    @Update
    suspend fun update(record: AttendanceEntity)

    @Query("SELECT * FROM attendance WHERE date = :date ORDER BY subjectId")
    fun getForDate(date: String): Flow<List<AttendanceEntity>>

    /** Session: calendar view needs a whole visible month's worth of records at once, for coloring the day grid. */
    @Query("SELECT * FROM attendance WHERE date BETWEEN :startDate AND :endDate")
    fun getBetween(startDate: String, endDate: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE subjectId = :subjectId ORDER BY date DESC")
    fun getForSubject(subjectId: Long): Flow<List<AttendanceEntity>>

    @Query(
        """
        SELECT s.id AS subjectId, s.name AS name, s.code AS code,
               SUM(CASE WHEN a.status = 'present' THEN 1 ELSE 0 END) AS present,
               SUM(CASE WHEN a.status = 'absent' THEN 1 ELSE 0 END) AS absent,
               SUM(CASE WHEN a.status = 'cancelled' THEN 1 ELSE 0 END) AS cancelled
        FROM subjects s
        LEFT JOIN attendance a ON a.subjectId = s.id
        GROUP BY s.id
        ORDER BY s.name ASC
        """
    )
    fun getSubjectSummaries(): Flow<List<SubjectSummary>>

    @Query("DELETE FROM attendance WHERE subjectId = :subjectId AND date = :date AND deviceId = :deviceId")
    suspend fun deleteRecord(subjectId: Long, date: String, deviceId: String)

    // --- One-shot (non-Flow) reads for the homescreen widget (Session 4) ---
    // The widget runs from an AppWidgetProvider / CoroutineWorker, not a
    // lifecycle-aware screen, so it wants a single snapshot rather than an
    // ongoing Flow subscription it would have to remember to cancel.

    @Query("SELECT * FROM attendance WHERE date >= :sinceDate ORDER BY date ASC")
    suspend fun getSince(sinceDate: String): List<AttendanceEntity>

    @Query(
        """
        SELECT SUM(CASE WHEN status = 'present' THEN 1 ELSE 0 END) AS present,
               SUM(CASE WHEN status = 'absent' THEN 1 ELSE 0 END) AS absent
        FROM attendance
        """
    )
    suspend fun getOverallCounts(): OverallCounts

    // --- One-shot reads for the GitHub publish export (Session 5) ---

    @Query("SELECT * FROM attendance ORDER BY date ASC")
    suspend fun getAllSnapshot(): List<AttendanceEntity>

    @Query(
        """
        SELECT s.id AS subjectId, s.name AS name, s.code AS code,
               SUM(CASE WHEN a.status = 'present' THEN 1 ELSE 0 END) AS present,
               SUM(CASE WHEN a.status = 'absent' THEN 1 ELSE 0 END) AS absent,
               SUM(CASE WHEN a.status = 'cancelled' THEN 1 ELSE 0 END) AS cancelled
        FROM subjects s
        LEFT JOIN attendance a ON a.subjectId = s.id
        GROUP BY s.id
        ORDER BY s.name ASC
        """
    )
    suspend fun getSubjectSummariesSnapshot(): List<SubjectSummary>
}
