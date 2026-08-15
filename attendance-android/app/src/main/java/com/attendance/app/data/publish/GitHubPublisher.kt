package com.attendance.app.data.publish

import android.util.Base64
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

data class PublishOutcome(
    val wasCreate: Boolean, // true = first push (file didn't exist yet), false = updated an existing file
    val htmlUrl: String?,
    val rawUrl: String
)

class GitHubPublishException(message: String) : Exception(message)

/**
 * Talks to the GitHub REST "Contents API" to create or update a single file
 * in a repo. https://docs.github.com/en/rest/repos/contents
 *
 * Always does a fresh GET immediately before the PUT to fetch the file's
 * current sha, rather than caching the sha from a previous publish — if the
 * file was ever edited outside this app (or from a second device), a stale
 * cached sha would make the PUT fail with a 409 conflict. The GET-then-PUT
 * is not atomic (a very unlucky race with another writer is technically
 * possible), but for one person publishing their own attendance from their
 * own devices, that's an acceptable risk for a big simplicity win.
 */
class GitHubPublisher {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun publish(settings: GitHubPublishSettings, jsonContent: String): PublishOutcome =
        withContext(Dispatchers.IO) {
            val contentsUrl = buildContentsUrl(settings)

            val existingSha = fetchExistingSha(settings, contentsUrl)
            val wasCreate = existingSha == null

            val requestBody = JsonObject().apply {
                addProperty("message", if (wasCreate) "Add attendance.json" else "Update attendance.json")
                addProperty("content", Base64.encodeToString(jsonContent.toByteArray(Charsets.UTF_8), Base64.NO_WRAP))
                addProperty("branch", settings.branch)
                if (existingSha != null) addProperty("sha", existingSha)
            }.toString()

            val request = Request.Builder()
                .url(contentsUrl)
                .header("Authorization", "Bearer ${settings.pat}")
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2022-11-28")
                .header("User-Agent", "attendance-app")
                .put(requestBody.toRequestBody(jsonMediaType))
                .build()

            client.newCall(request).execute().use { response ->
                val bodyString = response.body?.string().orEmpty()
                if (!response.isSuccessful) {
                    throw GitHubPublishException(describeError(response.code, bodyString))
                }
                val root = runCatching { JsonParser.parseString(bodyString).asJsonObject }.getOrNull()
                val htmlUrl = root?.getAsJsonObject("content")?.get("html_url")?.asString
                val rawUrl = "https://raw.githubusercontent.com/${settings.owner}/${settings.repo}/${settings.branch}/${settings.path}"
                PublishOutcome(wasCreate = wasCreate, htmlUrl = htmlUrl, rawUrl = rawUrl)
            }
        }

    /** Returns the file's current sha, or null if it doesn't exist yet (first push). */
    private fun fetchExistingSha(settings: GitHubPublishSettings, contentsUrl: String): String? {
        val request = Request.Builder()
            .url("$contentsUrl?ref=${settings.branch}")
            .header("Authorization", "Bearer ${settings.pat}")
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .header("User-Agent", "attendance-app")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            return when (response.code) {
                200 -> {
                    val bodyString = response.body?.string().orEmpty()
                    val root = runCatching { JsonParser.parseString(bodyString).asJsonObject }.getOrNull()
                        ?: throw GitHubPublishException("GitHub returned an unexpected response while checking for an existing file.")
                    root.get("sha")?.asString
                }
                404 -> null // file doesn't exist yet — this will be a first push
                else -> {
                    val bodyString = response.body?.string().orEmpty()
                    throw GitHubPublishException(describeError(response.code, bodyString))
                }
            }
        }
    }

    private fun buildContentsUrl(settings: GitHubPublishSettings): String {
        val encodedPath = settings.path.trim('/').split("/").joinToString("/") { java.net.URLEncoder.encode(it, "UTF-8") }
        return "https://api.github.com/repos/${settings.owner}/${settings.repo}/contents/$encodedPath"
    }

    private fun describeError(code: Int, bodyString: String): String {
        val apiMessage = runCatching { JsonParser.parseString(bodyString).asJsonObject.get("message")?.asString }.getOrNull()
        return when (code) {
            401 -> "GitHub rejected the token (401) — check the PAT is correct and hasn't expired."
            403 -> "GitHub denied the request (403) — the token may be missing \"Contents: Read and write\" permission for this repo, or you've hit a rate limit."
            404 -> "Repo or path not found (404) — check the owner/repo name and that the token can access this repo."
            409 -> "GitHub reported a conflict (409) — the file changed on GitHub between the check and the write. Try publishing again."
            422 -> "GitHub rejected the request (422)${apiMessage?.let { ": $it" } ?: ""} — often means the branch name is wrong."
            else -> "GitHub request failed (HTTP $code)${apiMessage?.let { ": $it" } ?: ""}"
        }
    }
}
