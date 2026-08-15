package com.attendance.app.data.publish

import com.attendance.app.data.AppDatabase
import com.attendance.app.data.AttendanceAggregation
import com.attendance.app.data.DayStatus
import com.google.gson.GsonBuilder
import java.time.Instant

/**
 * Builds the attendance.json payload from whatever's currently in the local
 * Room DB. No date-range cap here (unlike the widget's ~20-week lookback) —
 * this runs once when the user taps "Publish", not on a timer, so exporting
 * full history is cheap enough and gives the future website a complete
 * picture rather than a rolling window.
 */
class AttendanceJsonExporter(
    private val db: AppDatabase,
    private val deviceId: String
) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun buildPayload(): AttendancePayload {
        val allRecords = db.attendanceDao().getAllSnapshot()
        val subjectSummaries = db.attendanceDao().getSubjectSummariesSnapshot()
        val overallCounts = db.attendanceDao().getOverallCounts()

        val dayStatuses = AttendanceAggregation.aggregateByDay(allRecords)
        val days = dayStatuses.entries
            .sortedBy { it.key }
            .map { (date, status) ->
                DayPayload(date = date.toString(), status = status.toJsonString())
            }

        val subjects = subjectSummaries.map { s ->
            SubjectPayload(
                name = s.name,
                code = s.code,
                present = s.present,
                absent = s.absent,
                cancelled = s.cancelled,
                percentage = s.percentage?.toInt()
            )
        }

        return AttendancePayload(
            generatedAt = Instant.now().toString(),
            deviceId = deviceId,
            overall = OverallPayload(
                present = overallCounts.present,
                absent = overallCounts.absent,
                cancelled = subjects.sumOf { it.cancelled },
                percentage = overallCounts.percentage
            ),
            subjects = subjects,
            days = days
        )
    }

    fun toJsonString(payload: AttendancePayload): String = gson.toJson(payload)

    private fun DayStatus.toJsonString(): String = when (this) {
        DayStatus.PRESENT -> "present"
        DayStatus.ABSENT -> "absent"
        DayStatus.CANCELLED -> "cancelled"
    }
}
