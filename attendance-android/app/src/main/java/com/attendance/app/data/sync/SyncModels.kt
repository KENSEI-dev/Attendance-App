package com.attendance.app.data.sync

/**
 * One line of a changes_<deviceId>.jsonl file = one SyncEvent, serialized as JSON.
 *
 * IMPORTANT: subjects are identified by NAME here, not by local Room id.
 * Room's subjectId is auto-increment per-database, so the same subject can
 * have a different numeric id on every device. Name is the only thing that's
 * consistent across devices, so it's the join key during merge. (Subject
 * "code", e.g. "PCCCS403", is carried along too but name is authoritative.)
 *
 * Each device writes ONLY to its own file (changes_<its-own-device-id>.jsonl),
 * so there's never a two-writer race on the same file — Syncthing just has to
 * replicate each device's file to the others. This sidesteps most sync
 * conflicts entirely.
 */
sealed class SyncEvent {
    abstract val deviceId: String
    abstract val createdAt: String

    data class SubjectCreated(
        val name: String,
        val code: String?,
        override val deviceId: String,
        override val createdAt: String
    ) : SyncEvent()

    data class AttendanceMarked(
        val subjectName: String,
        val date: String,       // YYYY-MM-DD
        val status: String,     // present | absent | cancelled
        override val deviceId: String,
        override val createdAt: String
    ) : SyncEvent()
}

/** Plain-data mirror of SyncEvent used only for Gson (de)serialization. */
data class SyncEventJson(
    val type: String,               // "subject" | "attendance"
    val name: String? = null,
    val code: String? = null,
    val subjectName: String? = null,
    val date: String? = null,
    val status: String? = null,
    val deviceId: String,
    val createdAt: String
)

fun SyncEvent.toJsonModel(): SyncEventJson = when (this) {
    is SyncEvent.SubjectCreated -> SyncEventJson(
        type = "subject", name = name, code = code,
        deviceId = deviceId, createdAt = createdAt
    )
    is SyncEvent.AttendanceMarked -> SyncEventJson(
        type = "attendance", subjectName = subjectName, date = date, status = status,
        deviceId = deviceId, createdAt = createdAt
    )
}

fun SyncEventJson.toSyncEvent(): SyncEvent? = when (type) {
    "subject" -> name?.let { SyncEvent.SubjectCreated(it, code, deviceId, createdAt) }
    "attendance" -> if (subjectName != null && date != null && status != null) {
        SyncEvent.AttendanceMarked(subjectName, date, status, deviceId, createdAt)
    } else null
    else -> null
}
