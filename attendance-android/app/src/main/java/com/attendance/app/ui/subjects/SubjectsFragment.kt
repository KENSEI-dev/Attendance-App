package com.attendance.app.ui.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.attendance.app.databinding.FragmentSubjectsBinding
import com.attendance.app.util.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubjectsViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var adapter: SubjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SubjectsAdapter(onDelete = { viewModel.deleteSubject(it) })
        binding.subjectsList.layoutManager = LinearLayoutManager(requireContext())
        binding.subjectsList.adapter = adapter

        binding.addSubjectFab.setOnClickListener { showAddSubjectDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.subjects.collect { subjects ->
                    adapter.submitList(subjects)
                    binding.emptyState.visibility = if (subjects.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun showAddSubjectDialog() {
        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
        }
        val nameInput = EditText(requireContext()).apply { hint = "Subject name (e.g. Discrete Mathematics)" }
        val codeInput = EditText(requireContext()).apply { hint = "Code (e.g. PCCCS401) - optional" }
        container.addView(nameInput)
        container.addView(codeInput)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add subject")
            .setView(container)
            .setPositiveButton("Add") { _, _ ->
                viewModel.addSubject(nameInput.text.toString(), codeInput.text.toString())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
