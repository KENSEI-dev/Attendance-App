package com.attendance.app.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.attendance.app.databinding.FragmentCalendarBinding
import com.attendance.app.ui.attendance.AttendanceAdapter
import com.attendance.app.util.ViewModelFactory
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CalendarViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var dayAdapter: CalendarDayAdapter
    private lateinit var subjectAdapter: AttendanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dayAdapter = CalendarDayAdapter(onDayClick = { date -> viewModel.selectDate(date) })
        binding.calendarGrid.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarGrid.adapter = dayAdapter
        binding.calendarGrid.isNestedScrollingEnabled = false

        // Reuses the exact same adapter/row layout as the Attendance tab —
        // marking a subject here calls the same repository method, just with
        // whatever date is currently selected instead of always "today".
        subjectAdapter = AttendanceAdapter(onMark = { subjectId, status -> viewModel.mark(subjectId, status) })
        binding.calendarSubjectList.layoutManager = LinearLayoutManager(requireContext())
        binding.calendarSubjectList.adapter = subjectAdapter
        binding.calendarSubjectList.isNestedScrollingEnabled = false

        binding.prevMonthButton.setOnClickListener { viewModel.goToPreviousMonth() }
        binding.nextMonthButton.setOnClickListener { viewModel.goToNextMonth() }
        binding.calendarHolidayToggleButton.setOnClickListener { viewModel.toggleSelectedDateHoliday() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.visibleMonth.collect { month ->
                    val monthName = month.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    binding.monthLabel.text = "$monthName ${month.year}"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.calendarDays.collect { days -> dayAdapter.submitList(days) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedDate.collect { date ->
                    binding.selectedDateLabel.text =
                        date.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy", Locale.getDefault()))
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rows.collect { rows ->
                    subjectAdapter.submitList(rows)
                    updateSelectedDateVisibility()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isSelectedDateHoliday.collect { isHoliday ->
                    binding.calendarHolidayToggleButton.text = if (isHoliday) "Undo holiday" else "Mark as holiday"
                    updateSelectedDateVisibility()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Weekends are always a holiday now with no DB row behind
                // them, so hide the toggle rather than show a tap that does
                // nothing (see AttendanceFragment for the same treatment).
                viewModel.isSelectedDateHolidayOverridable.collect { overridable ->
                    binding.calendarHolidayToggleButton.visibility = if (overridable) View.VISIBLE else View.GONE
                }
            }
        }
    }

    /** Mirrors AttendanceFragment's three mutually-exclusive states, just for the selected date instead of today. */
    private fun updateSelectedDateVisibility() {
        val isHoliday = viewModel.isSelectedDateHoliday.value
        val hasRows = viewModel.rows.value.isNotEmpty()

        binding.calendarHolidayMessageGroup.visibility = if (isHoliday) View.VISIBLE else View.GONE
        binding.calendarSubjectList.visibility = if (!isHoliday && hasRows) View.VISIBLE else View.GONE
        binding.calendarEmptyState.visibility = if (!isHoliday && !hasRows) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
