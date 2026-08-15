package com.attendance.app.data.sync

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedWriter
import java.io.OutputStreamWriter

/**
 * Appends locally-created events (a subject added, an attendance mark) to
 * this device's own log file inside the sync folder. Never writes to any
 * other device's file.
 *
 * If no sync folder has been configured yet, append() is a silent no-op —
 * the app still works fully offline/local-only; the user can set up sync
 * whenever they're ready from the Sync tab.
 */
class SyncLogWriter(
    private val context: Context,
    private val folderManager: SyncFolderManager,
    private val deviceId: String
) {
    private val gson = Gson()

    fun append(event: SyncEvent) {
        if (!folderManager.hasFolderConfigured()) return

        val file = folderManager.getOrCreateOwnLogFile(deviceId) ?: return
        val line = gson.toJson(event.toJsonModel()) + "\n"

        try {
            // "wa" = write + append. Supported by most SAF document providers,
            // including the folder Syncthing apps expose.
            context.contentResolver.openOutputStream(file.uri, "wa")?.use { out ->
                BufferedWriter(OutputStreamWriter(out)).use { writer ->
                    writer.write(line)
                }
            }
        } catch (e: Exception) {
            // Sync is best-effort — a failed log write should never crash
            // or block the local attendance-marking flow that triggered it.
            e.printStackTrace()
        }
    }
}
