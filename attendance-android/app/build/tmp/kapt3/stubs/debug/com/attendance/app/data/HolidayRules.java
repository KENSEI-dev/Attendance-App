package com.attendance.app.data;

/**
 * Centralizes what counts as a holiday. Two sources, combined:
 * 1. An explicit DB row (HolidayEntity) — user-toggled, any date, via the
 *    "Mark/Undo holiday" button on the Attendance and Calendar tabs.
 * 2. A hardcoded rule — every Saturday and Sunday is always a holiday.
 *    No DB row involved, and for now no way to override it back to a
 *    working day (see [isOverridable]) — if a specific weekend needs to
 *    be a working day (e.g. a compensatory class), that's a real gap
 *    worth knowing about rather than silently unsupported.
 *
 * Before this, AttendanceViewModel and CalendarViewModel each queried the
 * holidays table independently — fine while "holiday" only ever meant "in
 * the DB," but a second source (the weekend rule) is exactly the kind of
 * thing that quietly drifts between two copies of the same logic if it
 * isn't pulled out to one place first.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/attendance/app/data/HolidayRules;", "", "()V", "isoFormat", "Ljava/time/format/DateTimeFormatter;", "kotlin.jvm.PlatformType", "isHoliday", "", "date", "Ljava/time/LocalDate;", "explicitHolidays", "", "Lcom/attendance/app/data/HolidayEntity;", "isOverridable", "isWeekend", "app_debug"})
public final class HolidayRules {
    private static final java.time.format.DateTimeFormatter isoFormat = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.attendance.app.data.HolidayRules INSTANCE = null;
    
    private HolidayRules() {
        super();
    }
    
    public final boolean isWeekend(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date) {
        return false;
    }
    
    public final boolean isHoliday(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, @org.jetbrains.annotations.NotNull()
    java.util.List<com.attendance.app.data.HolidayEntity> explicitHolidays) {
        return false;
    }
    
    /**
     * Whether the "Mark/Undo holiday" toggle should even be shown for this
     * date. False for a weekend — tapping it would try to remove a DB row
     * that was never there, so the date would keep showing as a holiday
     * anyway (the rule wins regardless), which just looks like a broken
     * button. Hiding it is more honest than showing a toggle that doesn't
     * toggle anything.
     */
    public final boolean isOverridable(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date) {
        return false;
    }
}