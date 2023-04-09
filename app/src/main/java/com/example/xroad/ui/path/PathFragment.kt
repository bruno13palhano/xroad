package com.example.xroad.ui.path

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.model.model.Difficulty
import com.example.xroad.R
import com.example.xroad.databinding.FragmentPathBinding
import com.example.xroad.ui.path.viewmodel.PathViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class PathFragment : Fragment() {
    private var _binding: FragmentPathBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PathViewModel by viewModels()

    private var pathId: Long = 0L
    private var difficulty = Difficulty.NORMAL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_path, container, false)
        val view = binding.root

        pathId = PathFragmentArgs.fromBundle(requireArguments()).pathId
        viewModel.getPath(pathId)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.uiEvents = this

        var duration = 0L
        var date = 0L

        val currentTimeAndDate = Calendar.getInstance()
        var currentHour = 1
        var currentMinute = 1
        var currentDay = 1
        var currentMoth = 1
        var currentYear = 1

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.pathDuration.collect {
                        duration = it
                        currentTimeAndDate.timeInMillis = it
                        currentHour = currentTimeAndDate.get(Calendar.HOUR_OF_DAY)
                        currentMinute = currentTimeAndDate.get(Calendar.MINUTE)
                    }
                }
                launch {
                    viewModel.pathDate.collect {
                        date = it
                        currentTimeAndDate.timeInMillis = it
                        currentDay = currentTimeAndDate.get(Calendar.DAY_OF_MONTH)
                        currentMoth = currentTimeAndDate.get(Calendar.MONTH)
                        currentYear = currentTimeAndDate.get(Calendar.YEAR)
                    }
                }
                launch {
                    viewModel.difficulty.collect {
                        difficulty = it
                    }
                }
            }
        }

        val timePicker = TimePickerDialog(requireContext(), R.style.CustomPicker, { _, hourOfDay, minute ->
            duration = durationToMilliseconds(minute, hourOfDay)
            viewModel.setPathDurationValue(duration)
        }, currentHour, currentMinute, true)

        binding.pathDuration.setOnClickListener {
            timePicker.updateTime(currentHour, currentMinute)
            timePicker.show()
        }

        val datePicker = DatePickerDialog(requireContext(), R.style.CustomPicker, { _, year, monthOfYear, dayOfMonth ->
            date = dateToMilliseconds(dayOfMonth, monthOfYear, year)
            viewModel.setPathDateValue(date)
        }, currentYear, currentMoth, currentDay)

        binding.pathDate.setOnClickListener {
            datePicker.updateDate(currentYear, currentMoth, currentDay)
            datePicker.show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updatePath() {
        findNavController().navigateUp()
        viewModel.update(pathId, difficulty)
    }

    private fun dateToMilliseconds(day: Int, month: Int, year: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        return calendar.timeInMillis
    }

    private fun durationToMilliseconds(minute: Int, hour: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.HOUR_OF_DAY, hour)

        return calendar.timeInMillis
    }
}