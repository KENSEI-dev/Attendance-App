package com.attendance.app.ui.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.app.data.publish.AttendanceJsonExporter
import com.attendance.app.data.publish.GitHubPublishSettings
import com.attendance.app.data.publish.GitHubPublisher
import com.attendance.app.data.publish.GitHubSettingsManager
import com.attendance.app.data.publish.PublishOutcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant

sealed class PublishUiState {
    data class Idle(val settings: GitHubPublishSettings?, val lastPublishedAt: String?) : PublishUiState()
    object Publishing : PublishUiState()
    data class Done(
        val settings: GitHubPublishSettings,
        val outcome: PublishOutcome,
        val lastPublishedAt: String
    ) : PublishUiState()
    data class Failed(val settings: GitHubPublishSettings?, val message: String) : PublishUiState()
}

class PublishViewModel(
    private val settingsManager: GitHubSettingsManager,
    private val exporter: AttendanceJsonExporter,
    private val publisher: GitHubPublisher
) : ViewModel() {

    private val _state = MutableStateFlow<PublishUiState>(
        PublishUiState.Idle(settingsManager.getSettings(), settingsManager.getLastPublishedAt())
    )
    val state: StateFlow<PublishUiState> = _state.asStateFlow()

    fun currentSettings(): GitHubPublishSettings? = settingsManager.getSettings()

    fun saveSettings(pat: String, owner: String, repo: String, path: String, branch: String) {
        val settings = GitHubPublishSettings(
            pat = pat.trim(),
            owner = owner.trim(),
            repo = repo.trim(),
            path = path.trim().ifBlank { GitHubSettingsManager.DEFAULT_PATH },
            branch = branch.trim().ifBlank { GitHubSettingsManager.DEFAULT_BRANCH }
        )
        settingsManager.saveSettings(settings)
        _state.value = PublishUiState.Idle(settings, settingsManager.getLastPublishedAt())
    }

    fun publishNow() {
        val settings = settingsManager.getSettings() ?: return
        if (settings.pat.isBlank() || settings.owner.isBlank() || settings.repo.isBlank()) {
            _state.value = PublishUiState.Failed(settings, "Fill in the token, owner, and repo before publishing.")
            return
        }
        _state.value = PublishUiState.Publishing
        viewModelScope.launch {
            try {
                val payload = exporter.buildPayload()
                val json = exporter.toJsonString(payload)
                val outcome = publisher.publish(settings, json)
                val now = Instant.now().toString()
                settingsManager.setLastPublishedAt(now)
                _state.value = PublishUiState.Done(settings, outcome, now)
            } catch (e: Exception) {
                _state.value = PublishUiState.Failed(settings, e.message ?: "Unknown error")
            }
        }
    }
}
