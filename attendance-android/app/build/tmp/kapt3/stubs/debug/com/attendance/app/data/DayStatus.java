package com.attendance.app.data;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/attendance/app/data/DayStatus;", "", "(Ljava/lang/String;I)V", "PRESENT", "ABSENT", "CANCELLED", "app_debug"})
public enum DayStatus {
    /*public static final*/ PRESENT /* = new PRESENT() */,
    /*public static final*/ ABSENT /* = new ABSENT() */,
    /*public static final*/ CANCELLED /* = new CANCELLED() */;
    
    DayStatus() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.attendance.app.data.DayStatus> getEntries() {
        return null;
    }
}