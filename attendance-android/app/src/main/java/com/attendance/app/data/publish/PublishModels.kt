package com.attendance.app.data.publish

/**
 * Shape of the attendance.json file pushed to GitHub. The Session 6 Vercel
 * dashboard fetches this file raw from raw.githubusercontent.com and is
 * expected to render its own dot-matrix + summary from exactly this shape —
 * keep this in sync with whatever Session 6 ends up expecting.
 */
data class AttendancePayload(
    val generatedAt: String,
    val deviceId: String,
    val overall: OverallPayload,
    val subjects: List<SubjectPayload>,
    val days: List<DayPayload>
)

data class OverallPayload(
    val present: Int,
    val absent: Int,
    val cancelled: Int,
    val percentage: Int?
)

data class SubjectPayload(
    val name: String,
    val code: String?,
    val present: Int,
    val absent: Int,
    val cancelled: Int,
    val percentage: Int?
)

/** One entry per day that has at least one record — days with nothing marked are simply omitted. */
data class DayPayload(
    val date: String, // YYYY-MM-DD
    val status: String // "present" | "absent" | "cancelled"
)
