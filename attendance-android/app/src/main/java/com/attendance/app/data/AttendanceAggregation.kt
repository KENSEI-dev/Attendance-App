package com.attendance.app.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * One attendance status per calendar day, collapsed across every subject
 * (and every device's records for that day — dedup by (subject,date,device)
 * already happened at merge time in Session 3, this just collapses subjects).
 *
 * Used by the homescreen widget (Session 4) and the GitHub JSON export
 * (Session 5) so both ever agree on what a day's dot color means — this used
 * to live only inside the widget's bitmap renderer; pulling it out here
 * means the exported JSON and the widget can't quietly drift apart.
 */
enum class DayStatus { PRESENT, ABSENT, CANCELLED }

object AttendanceAggregation {

    private val isoDate: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    /**
     * Priority when a day has mixed results across subjects: absent > present
     * > cancelled, so a day you missed anything on never gets hidden behind
     * a day you also attended something else.
     */
    fun aggregateByDay(records: List<AttendanceEntity>): Map<LocalDate, DayStatus> {
        val result = HashMap<LocalDate, DayStatus>()
        for (record in records) {
            val date = try {
                LocalDate.parse(record.date, isoDate)
            } catch (e: Exception) {
                continue // skip malformed rows rather than crash
            }

            val candidate = when (record.status) {
                AttendanceStatus.ABSENT -> DayStatus.ABSENT
                AttendanceStatus.PRESENT -> DayStatus.PRESENT
                AttendanceStatus.CANCELLED -> DayStatus.CANCELLED
                else -> continue
            }
            val existing = result[date]
            result[date] = when {
                existing == null -> candidate
                existing == DayStatus.ABSENT || candidate == DayStatus.ABSENT -> DayStatus.ABSENT
                existing == DayStatus.PRESENT || candidate == DayStatus.PRESENT -> DayStatus.PRESENT
                else -> DayStatus.CANCELLED
            }
        }
        return result
    }
}
