package com.attendance.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Mirrors db/schema.sql `subjects` table from Session 1.
 * Keep field names and semantics identical to the Python schema —
 * this table gets read/written by both the CLI tool and this app
 * against the same physical attendance.db file once sync is wired up.
 */
@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val code: String?,
    val createdAt: String
)

/**
 * Mirrors db/schema.sql `attendance` table from Session 1.
 *
 * status is one of: "present" | "absent" | "cancelled"
 * deviceId + subjectId + date is the unique/dedup key used later
 * for merging records synced in from other devices (Session 3).
 */
@Entity(
    tableName = "attendance",
    indices = [Index(value = ["subjectId", "date", "deviceId"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Long,
    val date: String,       // ISO format YYYY-MM-DD
    val status: String,     // present | absent | cancelled
    val deviceId: String,
    val createdAt: String
)

object AttendanceStatus {
    const val PRESENT = "present"
    const val ABSENT = "absent"
    const val CANCELLED = "cancelled"
}

/** Result row for the subject-wise summary query, same shape as matrix.py's summary table. */
data class SubjectSummary(
    val subjectId: Long,
    val name: String,
    val code: String?,
    val present: Int,
    val absent: Int,
    val cancelled: Int
) {
    val marked: Int get() = present + absent
    val percentage: Double? get() = if (marked > 0) (present.toDouble() / marked) * 100 else null
}

/**
 * All-time present/absent totals across every subject, used by the
 * homescreen widget (Session 4) for the headline percentage. Cancelled
 * days are intentionally excluded, same as SubjectSummary.percentage.
 */
data class OverallCounts(val present: Int, val absent: Int) {
    val marked: Int get() = present + absent
    val percentage: Int? get() = if (marked > 0) ((present * 100) / marked) else null
}
