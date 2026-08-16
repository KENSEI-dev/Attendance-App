package com.attendance.app.data

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Centralizes what counts as a holiday. Two sources, combined:
 *  1. An explicit DB row (HolidayEntity) — user-toggled, any date, via the
 *     "Mark/Undo holiday" button on the Attendance and Calendar tabs.
 *  2. A hardcoded rule — every Saturday and Sunday is always a holiday.
 *     No DB row involved, and for now no way to override it back to a
 *     working day (see [isOverridable]) — if a specific weekend needs to
 *     be a working day (e.g. a compensatory class), that's a real gap
 *     worth knowing about rather than silently unsupported.
 *
 * Before this, AttendanceViewModel and CalendarViewModel each queried the
 * holidays table independently — fine while "holiday" only ever meant "in
 * the DB," but a second source (the weekend rule) is exactly the kind of
 * thing that quietly drifts between two copies of the same logic if it
 * isn't pulled out to one place first.
 */
object HolidayRules {
    private val isoFormat = DateTimeFormatter.ISO_LOCAL_DATE

    fun isWeekend(date: LocalDate): Boolean =
        date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY

    fun isHoliday(date: LocalDate, explicitHolidays: List<HolidayEntity>): Boolean {
        if (isWeekend(date)) return true
        val dateStr = date.format(isoFormat)
        return explicitHolidays.any { it.date == dateStr }
    }

    /**
     * Whether the "Mark/Undo holiday" toggle should even be shown for this
     * date. False for a weekend — tapping it would try to remove a DB row
     * that was never there, so the date would keep showing as a holiday
     * anyway (the rule wins regardless), which just looks like a broken
     * button. Hiding it is more honest than showing a toggle that doesn't
     * toggle anything.
     */
    fun isOverridable(date: LocalDate): Boolean = !isWeekend(date)
}
