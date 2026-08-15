package com.attendance.app.ui.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.attendance.app.data.publish.GitHubSettingsManager
import com.attendance.app.databinding.FragmentPublishBinding
import com.attendance.app.util.ViewModelFactory
import kotlinx.coroutines.launch

class PublishFragment : Fragment() {

    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PublishViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefillFromSavedSettings()

        binding.saveSettingsButton.setOnClickListener {
            viewModel.saveSettings(
                pat = binding.patInput.text.toString(),
                owner = binding.ownerInput.text.toString(),
                repo = binding.repoInput.text.toString(),
                path = binding.pathInput.text.toString(),
                branch = binding.branchInput.text.toString()
            )
        }

        binding.publishButton.setOnClickListener {
            // Settings must be saved before publishing — save whatever's
            // currently typed first so tapping "Publish" alone still works.
            viewModel.saveSettings(
                pat = binding.patInput.text.toString(),
                owner = binding.ownerInput.text.toString(),
                repo = binding.repoInput.text.toString(),
                path = binding.pathInput.text.toString(),
                branch = binding.branchInput.text.toString()
            )
            viewModel.publishNow()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state -> render(state) }
            }
        }
    }

    private fun prefillFromSavedSettings() {
        val settings = viewModel.currentSettings()
        if (settings != null) {
            binding.patInput.setText(settings.pat)
            binding.ownerInput.setText(settings.owner)
            binding.repoInput.setText(settings.repo)
            binding.pathInput.setText(settings.path)
            binding.branchInput.setText(settings.branch)
        } else {
            binding.pathInput.setText(GitHubSettingsManager.DEFAULT_PATH)
            binding.branchInput.setText(GitHubSettingsManager.DEFAULT_BRANCH)
        }
    }

    private fun render(state: PublishUiState) {
        when (state) {
            is PublishUiState.Idle -> {
                binding.publishButton.isEnabled = true
                binding.publishedUrlText.visibility = View.GONE
                binding.publishStatusText.text = describeLastPublished(state.lastPublishedAt)
            }
            is PublishUiState.Publishing -> {
                binding.publishButton.isEnabled = false
                binding.publishedUrlText.visibility = View.GONE
                binding.publishStatusText.text = "Publishing…"
            }
            is PublishUiState.Done -> {
                binding.publishButton.isEnabled = true
                val action = if (state.outcome.wasCreate) "Created" else "Updated"
                binding.publishStatusText.text = "$action attendance.json on GitHub — ${describeLastPublished(state.lastPublishedAt)}"
                binding.publishedUrlText.text = state.outcome.rawUrl
                binding.publishedUrlText.visibility = View.VISIBLE
            }
            is PublishUiState.Failed -> {
                binding.publishButton.isEnabled = true
                binding.publishedUrlText.visibility = View.GONE
                binding.publishStatusText.text = "Publish failed: ${state.message}"
            }
        }
    }

    private fun describeLastPublished(lastPublishedAt: String?): String {
        return if (lastPublishedAt == null) {
            "Not published yet."
        } else {
            "Last published: $lastPublishedAt"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
