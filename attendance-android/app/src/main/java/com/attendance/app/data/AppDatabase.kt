package com.attendance.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [SubjectEntity::class, AttendanceEntity::class, HolidayEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun holidayDao(): HolidayDao

    companion object {
        // Named to match the CLI tool's default db filename, so both
        // point at "attendance.db" conceptually. The actual on-disk path
        // used for cross-device sync (via Syncthing) gets wired up in
        // Session 3 — for now this lives in normal app-private storage.
        private const val DB_NAME = "attendance.db"

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
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `holidays` (
                        `date` TEXT NOT NULL,
                        `reason` TEXT,
                        `createdAt` TEXT NOT NULL,
                        PRIMARY KEY(`date`)
                    )
                    """.trimIndent()
                )
            }
        }

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).addMigrations(MIGRATION_1_2).build().also { INSTANCE = it }
            }
        }
    }
}
