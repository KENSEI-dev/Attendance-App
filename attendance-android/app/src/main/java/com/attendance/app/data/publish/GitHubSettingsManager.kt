package com.attendance.app.data.publish

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

data class GitHubPublishSettings(
    val pat: String,
    val owner: String,
    val repo: String,
    val path: String,
    val branch: String
)

/**
 * Stores the fine-grained PAT and repo target in EncryptedSharedPreferences
 * rather than plain SharedPreferences — this file otherwise sits in the
 * app's private storage as an unencrypted XML file, which is a bigger deal
 * to get wrong for a credential than for e.g. the device_id string
 * elsewhere in this app. Everything else here (owner/repo/path/branch)
 * isn't sensitive, but it's simplest to keep the whole settings blob in one
 * encrypted file rather than splitting it across two SharedPreferences.
 */
class GitHubSettingsManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val PREFS_NAME = "attendance_github_publish_secure"
        private const val KEY_PAT = "pat"
        private const val KEY_OWNER = "owner"
        private const val KEY_REPO = "repo"
        private const val KEY_PATH = "path"
        private const val KEY_BRANCH = "branch"
        private const val KEY_LAST_PUBLISHED_AT = "last_published_at"

        const val DEFAULT_PATH = "attendance.json"
        const val DEFAULT_BRANCH = "main"
    }

    fun getSettings(): GitHubPublishSettings? {
        val pat = prefs.getString(KEY_PAT, null) ?: return null
        val owner = prefs.getString(KEY_OWNER, null) ?: return null
        val repo = prefs.getString(KEY_REPO, null) ?: return null
        val path = prefs.getString(KEY_PATH, DEFAULT_PATH) ?: DEFAULT_PATH
        val branch = prefs.getString(KEY_BRANCH, DEFAULT_BRANCH) ?: DEFAULT_BRANCH
        return GitHubPublishSettings(pat, owner, repo, path, branch)
    }

    fun saveSettings(settings: GitHubPublishSettings) {
        prefs.edit()
            .putString(KEY_PAT, settings.pat)
            .putString(KEY_OWNER, settings.owner)
            .putString(KEY_REPO, settings.repo)
            .putString(KEY_PATH, settings.path)
            .putString(KEY_BRANCH, settings.branch)
            .apply()
    }

    fun clearSettings() {
        prefs.edit().clear().apply()
    }

    fun getLastPublishedAt(): String? = prefs.getString(KEY_LAST_PUBLISHED_AT, null)

    fun setLastPublishedAt(isoTimestamp: String) {
        prefs.edit().putString(KEY_LAST_PUBLISHED_AT, isoTimestamp).apply()
    }
}
