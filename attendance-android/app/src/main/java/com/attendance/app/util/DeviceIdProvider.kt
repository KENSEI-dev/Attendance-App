package com.attendance.app.util

import android.content.Context
import java.util.UUID

/**
 * Generates and persists a stable device_id the first time the app runs.
 * This is the same device_id concept used in db/schema.sql and the
 * seed script from Session 1 — it's what lets the future sync merge
 * (Session 3) tell "my write" apart from "a write that arrived from
 * another device via changes.jsonl".
 */
object DeviceIdProvider {
    private const val PREFS_NAME = "attendance_prefs"
    private const val KEY_DEVICE_ID = "device_id"

    fun getOrCreate(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val existing = prefs.getString(KEY_DEVICE_ID, null)
        if (existing != null) return existing

        val newId = "android-${UUID.randomUUID().toString().take(8)}"
        prefs.edit().putString(KEY_DEVICE_ID, newId).apply()
        return newId
    }
}
