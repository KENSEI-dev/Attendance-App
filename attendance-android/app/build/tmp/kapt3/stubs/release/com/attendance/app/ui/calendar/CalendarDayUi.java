package com.attendance.app.ui.calendar;

/**
 * One cell in the month grid. `date == null` renders as a blank leading-padding cell before the 1st.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\nH\u00c6\u0003JI\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001e"}, d2 = {"Lcom/attendance/app/ui/calendar/CalendarDayUi;", "", "date", "Ljava/time/LocalDate;", "isSelected", "", "isToday", "isFuture", "isHoliday", "status", "Lcom/attendance/app/data/DayStatus;", "(Ljava/time/LocalDate;ZZZZLcom/attendance/app/data/DayStatus;)V", "getDate", "()Ljava/time/LocalDate;", "()Z", "getStatus", "()Lcom/attendance/app/data/DayStatus;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "", "app_release"})
public final class CalendarDayUi {
    @org.jetbrains.annotations.Nullable()
    private final java.time.LocalDate date = null;
    private final boolean isSelected = false;
    private final boolean isToday = false;
    private final boolean isFuture = false;
    private final boolean isHoliday = false;
    @org.jetbrains.annotations.Nullable()
    private final com.attendance.app.data.DayStatus status = null;
    
    public CalendarDayUi(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate date, boolean isSelected, boolean isToday, boolean isFuture, boolean isHoliday, @org.jetbrains.annotations.Nullable()
    com.attendance.app.data.DayStatus status) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate getDate() {
        return null;
    }
    
    public final boolean isSelected() {
        return false;
    }
    
    public final boolean isToday() {
        return false;
    }
    
    public final boolean isFuture() {
        return false;
    }
    
    public final boolean isHoliday() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.attendance.app.data.DayStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.attendance.app.data.DayStatus component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.attendance.app.ui.calendar.CalendarDayUi copy(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate date, boolean isSelected, boolean isToday, boolean isFuture, boolean isHoliday, @org.jetbrains.annotations.Nullable()
    com.attendance.app.data.DayStatus status) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}