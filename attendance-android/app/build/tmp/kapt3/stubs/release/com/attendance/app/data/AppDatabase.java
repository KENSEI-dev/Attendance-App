package com.attendance.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/attendance/app/data/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "attendanceDao", "Lcom/attendance/app/data/AttendanceDao;", "holidayDao", "Lcom/attendance/app/data/HolidayDao;", "subjectDao", "Lcom/attendance/app/data/SubjectDao;", "Companion", "app_release"})
@androidx.room.Database(entities = {com.attendance.app.data.SubjectEntity.class, com.attendance.app.data.AttendanceEntity.class, com.attendance.app.data.HolidayEntity.class}, version = 2, exportSchema = true)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DB_NAME = "attendance.db";
    
    /**
     * Adds the holidays table. Deliberately a real migration, not
     * fallbackToDestructiveMigration() — by this point real attendance
     * data exists on test devices from actual use, and a destructive
     * migration would silently wipe it on the next app update. The
     * column order/types/nullability here must match what Room
     * generates from HolidayEntity exactly, or Room's schema validation
     * will reject it at runtime — this couldn't be verified against a
     * real Room build in this environment, so it's worth double
     * checking on first run after upgrading (Logcat will say clearly
     * if the migration's resulting schema doesn't match what Room
     * expected).
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.attendance.app.data.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.attendance.app.data.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.attendance.app.data.SubjectDao subjectDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.attendance.app.data.AttendanceDao attendanceDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.attendance.app.data.HolidayDao holidayDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000e"}, d2 = {"Lcom/attendance/app/data/AppDatabase$Companion;", "", "()V", "DB_NAME", "", "INSTANCE", "Lcom/attendance/app/data/AppDatabase;", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "getMIGRATION_1_2", "()Landroidx/room/migration/Migration;", "getInstance", "context", "Landroid/content/Context;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Adds the holidays table. Deliberately a real migration, not
         * fallbackToDestructiveMigration() — by this point real attendance
         * data exists on test devices from actual use, and a destructive
         * migration would silently wipe it on the next app update. The
         * column order/types/nullability here must match what Room
         * generates from HolidayEntity exactly, or Room's schema validation
         * will reject it at runtime — this couldn't be verified against a
         * real Room build in this environment, so it's worth double
         * checking on first run after upgrading (Logcat will say clearly
         * if the migration's resulting schema doesn't match what Room
         * expected).
         */
        @org.jetbrains.annotations.NotNull()
        public final androidx.room.migration.Migration getMIGRATION_1_2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.attendance.app.data.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}