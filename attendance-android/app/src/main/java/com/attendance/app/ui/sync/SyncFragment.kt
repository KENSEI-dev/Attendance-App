package com.attendance.app.ui.sync

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.attendance.app.data.sync.SyncFolderManager
import com.attendance.app.databinding.FragmentSyncBinding
import com.attendance.app.util.ViewModelFactory
import kotlinx.coroutines.launch

class SyncFragment : Fragment() {

    private var _binding: FragmentSyncBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SyncViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private val folderPickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        if (uri != null) {
            SyncFolderManager(requireContext()).persistFolderChoice(uri)
            viewModel.onFolderChosen()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSyncBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deviceIdValue.text = viewModel.deviceIdLabel

        binding.chooseFolderButton.setOnClickListener {
            folderPickerLauncher.launch(null)
        }
        binding.forgetFolderButton.setOnClickListener {
            viewModel.forgetFolder()
        }
        binding.syncNowButton.setOnClickListener {
            viewModel.syncNow()
        }
        binding.cleanupConflictsButton.setOnClickListener {
            viewModel.archiveConflicts()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state -> render(state) }
            }
        }
    }

    private fun render(state: SyncUiState) {
        when (state) {
            is SyncUiState.NoFolderConfigured -> {
                binding.folderStatus.text = "No sync folder set up yet."
                binding.folderStatus.setTextColor(resources.getColor(android.R.color.darker_gray, null))
                setConfiguredControlsVisible(false)
                binding.syncResultText.text = "Set up your Syncthing-shared folder to enable cross-device sync."
            }
            is SyncUiState.Ready -> {
                binding.folderStatus.text = "Sync folder: ${state.folderName}"
                binding.folderStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
                setConfiguredControlsVisible(true)
                binding.syncNowButton.isEnabled = true
                binding.cleanupConflictsButton.visibility = View.GONE
                binding.syncResultText.text = "Ready to sync. Tap \"Sync now\" to pull in changes from other devices."
            }
            is SyncUiState.Syncing -> {
                setConfiguredControlsVisible(true)
                binding.syncNowButton.isEnabled = false
                binding.cleanupConflictsButton.isEnabled = false
                binding.syncResultText.text = "Syncing…"
            }
            is SyncUiState.Done -> {
                binding.folderStatus.text = "Sync folder: ${state.folderName}"
                setConfiguredControlsVisible(true)
                binding.syncNowButton.isEnabled = true
                val r = state.result
                binding.syncResultText.text = buildString {
                    if (state.cleanupNote != null) {
                        append(state.cleanupNote)
                        append("\n\n")
                    }
                    append("Last sync: read ${r.filesRead} device log(s)")
                    if (r.conflictFilesFound > 0) append(" (${r.conflictFilesFound} were sync-conflict copies)")
                    append(", ${r.eventsProcessed} event(s) processed — ")
                    append("${r.subjectsCreated} new subject(s), ")
                    append("${r.attendanceCreated} new attendance record(s)")
                    if (r.attendanceUpdated > 0) append(", ${r.attendanceUpdated} updated to a more recent mark")
                    append(".")
                    if (r.conflictFilesFound > 0) {
                        append("\n\nSync-conflict files are already merged safely — repeated conflicts usually mean ")
                        append("two devices ended up sharing the same device ID. Tap \"Clean up conflict files\" ")
                        append("below once you're not worried about that, so they stop being re-read every sync.")
                    }
                    if (r.errors.isNotEmpty()) {
                        append("\n\nErrors:\n")
                        append(r.errors.joinToString("\n"))
                    }
                }
                binding.cleanupConflictsButton.isEnabled = true
                binding.cleanupConflictsButton.visibility =
                    if (r.conflictFilesFound > 0) View.VISIBLE else View.GONE
            }
            is SyncUiState.Failed -> {
                binding.folderStatus.text = "Sync folder: ${state.folderName}"
                setConfiguredControlsVisible(true)
                binding.syncNowButton.isEnabled = true
                binding.cleanupConflictsButton.visibility = View.GONE
                binding.syncResultText.text = "Sync failed: ${state.message}"
            }
        }
    }

    private fun setConfiguredControlsVisible(visible: Boolean) {
        binding.syncNowButton.visibility = if (visible) View.VISIBLE else View.GONE
        binding.forgetFolderButton.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
