package com.attendance.app.ui.attendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.attendance.app.databinding.FragmentAttendanceBinding
import com.attendance.app.util.ViewModelFactory
import kotlinx.coroutines.launch

class AttendanceFragment : Fragment() {

    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AttendanceViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var adapter: AttendanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AttendanceAdapter(onMark = { subjectId, status -> viewModel.mark(subjectId, status) })
        binding.attendanceList.layoutManager = LinearLayoutManager(requireContext())
        binding.attendanceList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.today.collect { today ->
                    binding.todayLabel.text = "Today — $today"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rows.collect { rows ->
                    adapter.submitList(rows)
                    binding.emptyState.visibility = if (rows.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // The actual fix: re-check the system date every time this tab comes
        // back into view, not just when the ViewModel happens to be freshly
        // created. Cheap no-op if the date hasn't changed.
        viewModel.refreshToday()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
