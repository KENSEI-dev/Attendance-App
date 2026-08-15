package com.attendance.app.ui.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendance.app.data.sync.SyncEngine
import com.attendance.app.data.sync.SyncFolderManager
import com.attendance.app.data.sync.SyncResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SyncUiState {
    object NoFolderConfigured : SyncUiState()
    data class Ready(val folderName: String) : SyncUiState()
    object Syncing : SyncUiState()
    data class Done(
        val folderName: String,
        val result: SyncResult,
        // Session 7: set only right after a "Clean up conflict files" tap,
        // shown once above the regular sync summary, then not carried forward.
        val cleanupNote: String? = null
    ) : SyncUiState()
    data class Failed(val folderName: String, val message: String) : SyncUiState()
}

class SyncViewModel(
    private val folderManager: SyncFolderManager,
    private val syncEngine: SyncEngine,
    private val deviceId: String
) : ViewModel() {

    val deviceIdLabel: String get() = deviceId

    private val _state = MutableStateFlow(currentReadyOrEmptyState())
    val state: StateFlow<SyncUiState> = _state.asStateFlow()

    private fun currentReadyOrEmptyState(): SyncUiState {
        val name = folderManager.getFolderDisplayName()
        return if (name != null) SyncUiState.Ready(name) else SyncUiState.NoFolderConfigured
    }

    fun onFolderChosen() {
        _state.value = currentReadyOrEmptyState()
    }

    fun forgetFolder() {
        folderManager.clearFolderChoice()
        _state.value = SyncUiState.NoFolderConfigured
    }

    fun syncNow() {
        val folderName = folderManager.getFolderDisplayName() ?: return
        _state.value = SyncUiState.Syncing
        viewModelScope.launch {
            try {
                val result = syncEngine.syncNow()
                _state.value = SyncUiState.Done(folderName, result)
            } catch (e: Exception) {
                _state.value = SyncUiState.Failed(folderName, e.message ?: "Unknown error")
            }
        }
    }

    /**
     * Session 7: renames already-merged *.sync-conflict-*.jsonl files out of
     * the way, then re-syncs so the displayed summary reflects the cleaned-up
     * folder (conflictFilesFound should read 0 afterward).
     */
    fun archiveConflicts() {
        val folderName = folderManager.getFolderDisplayName() ?: return
        _state.value = SyncUiState.Syncing
        viewModelScope.launch {
            try {
                val archiveResult = folderManager.archiveConflictFiles()
                val result = syncEngine.syncNow()
                val note = if (archiveResult.failed > 0) {
                    "Archived ${archiveResult.archived} file(s); ${archiveResult.failed} couldn't be renamed (check folder permissions)."
                } else {
                    "Archived ${archiveResult.archived} conflict file(s)."
                }
                _state.value = SyncUiState.Done(folderName, result, cleanupNote = note)
            } catch (e: Exception) {
                _state.value = SyncUiState.Failed(folderName, e.message ?: "Unknown error")
            }
        }
    }
}
