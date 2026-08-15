package com.attendance.app.data.sync

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.documentfile.provider.DocumentFile

/**
 * Manages the "sync folder" — a folder the user points the app at, expected
 * to be the same folder Syncthing is keeping in sync across their devices.
 *
 * Android's scoped storage means the app can't just read/write an arbitrary
 * path; the user has to grant access once via the system folder picker
 * (Storage Access Framework), and we persist that permission so it survives
 * app restarts and device reboots.
 */
class SyncFolderManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("attendance_prefs", Context.MODE_PRIVATE)

    fun hasFolderConfigured(): Boolean = getFolderUri() != null

    fun getFolderUri(): Uri? {
        val stored = prefs.getString(KEY_FOLDER_URI, null) ?: return null
        val uri = Uri.parse(stored)
        // Confirm we still actually hold a persisted permission for it —
        // it can be revoked externally (e.g. user clears app storage access).
        val stillGranted = context.contentResolver.persistedUriPermissions.any {
            it.uri == uri && it.isReadPermission && it.isWritePermission
        }
        return if (stillGranted) uri else null
    }

    fun getFolderDisplayName(): String? {
        val uri = getFolderUri() ?: return null
        return DocumentFile.fromTreeUri(context, uri)?.name
    }

    /** Call this from an Activity/Fragment's ActivityResultLauncher callback. */
    fun persistFolderChoice(treeUri: Uri) {
        context.contentResolver.takePersistableUriPermission(
            treeUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
        prefs.edit().putString(KEY_FOLDER_URI, treeUri.toString()).apply()
    }

    fun clearFolderChoice() {
        prefs.edit().remove(KEY_FOLDER_URI).apply()
    }

    fun getFolder(): DocumentFile? {
        val uri = getFolderUri() ?: return null
        return DocumentFile.fromTreeUri(context, uri)
    }

    /** Returns all changes_*.jsonl files currently in the folder, including any sync-conflict copies. */
    fun listChangeFiles(): List<DocumentFile> {
        val folder = getFolder() ?: return emptyList()
        return folder.listFiles().filter {
            it.name?.startsWith("changes_") == true && it.name?.endsWith(".jsonl") == true
        }
    }

    /** Just the subset of [listChangeFiles] that Syncthing marked as a conflict copy. */
    fun listConflictFiles(): List<DocumentFile> = listChangeFiles().filter { isConflictFileName(it.name) }

    /** Finds or creates this device's own log file: changes_<deviceId>.jsonl */
    fun getOrCreateOwnLogFile(deviceId: String): DocumentFile? {
        val folder = getFolder() ?: return null
        val fileName = "changes_$deviceId.jsonl"
        return folder.findFile(fileName)
            ?: folder.createFile("application/x-ndjson", fileName)
    }

    /**
     * Session 7: renames every current conflict file to an "archived_" prefix
     * instead of deleting it. Its events are already merged into every
     * device's DB by this point — nothing is lost either way — but renaming
     * (a) stops it matching the "changes_" prefix so it's excluded from
     * future scans (no more re-parsing it forever), and (b) keeps the file
     * itself around as an audit trail in case something ever looks wrong,
     * rather than an irreversible delete that also has to propagate through
     * Syncthing to every other device.
     */
    fun archiveConflictFiles(): ArchiveResult {
        var archived = 0
        var failed = 0
        for (file in listConflictFiles()) {
            val originalName = file.name ?: continue
            val renamed = runCatching { file.renameTo("archived_$originalName") }.getOrDefault(false)
            if (renamed) archived++ else failed++
        }
        return ArchiveResult(archived, failed)
    }

    companion object {
        private const val KEY_FOLDER_URI = "sync_folder_uri"
        private const val CONFLICT_MARKER = ".sync-conflict-"

        /** Matches Syncthing's own conflict-copy naming: name.sync-conflict-YYYYMMDD-HHMMSS-XXXXXXX.ext */
        fun isConflictFileName(name: String?): Boolean = name?.contains(CONFLICT_MARKER) == true
    }
}

data class ArchiveResult(val archived: Int, val failed: Int)
