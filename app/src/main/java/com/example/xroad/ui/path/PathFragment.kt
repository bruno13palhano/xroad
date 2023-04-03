package com.example.xroad.ui.path

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.DateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.model.model.Difficulty
import com.example.model.model.Path
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPathBinding.inflate(inflater, container, false)
        val view = binding.root

        val roadId = PathFragmentArgs.fromBundle(requireArguments()).pathId

        var title = ""
        var topic = ""
        var description = ""
        var duration = 0L
        var date = 0L
        var difficulty = Difficulty.NORMAL

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
                        binding.pathDuration.text = durationInMillisecondsToString(it)
                    }
                }
                launch {
                    viewModel.pathDate.collect {
                        binding.pathDate.text = dateInMillisecondsToString(it)
                    }
                }
                launch {
                    viewModel.getPath(roadId).collect {
                        binding.pathTitle.setText(it.title)
                        title = it.title

                        binding.pathTopic.setText(it.topic)
                        topic = it.topic

                        binding.pathDescription.setText(it.description)
                        description = it.description

                        binding.pathDuration.text =
                            durationInMillisecondsToString(it.durationInMilliseconds)
                        duration = it.durationInMilliseconds

                        binding.pathDate.text = dateInMillisecondsToString(it.dateInMilliseconds)
                        date = it.dateInMilliseconds

                        setChipDifficulty(it.difficulty)

                        currentTimeAndDate.timeInMillis = it.durationInMilliseconds
                        currentHour = currentTimeAndDate.get(Calendar.HOUR_OF_DAY)
                        currentMinute = currentTimeAndDate.get(Calendar.MINUTE)

                        currentTimeAndDate.timeInMillis = it.dateInMilliseconds
                        currentDay = currentTimeAndDate.get(Calendar.DAY_OF_MONTH)
                        currentMoth = currentTimeAndDate.get(Calendar.MONTH)
                        currentYear = currentTimeAndDate.get(Calendar.YEAR)
                    }
                }
            }
        }

        val timePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            duration = durationToMilliseconds(minute, hourOfDay)
            viewModel.setPathDurationValue(duration)
        }, currentHour, currentMinute, true)

        binding.pathDuration.setOnClickListener {
            timePicker.updateTime(currentHour, currentMinute)
            timePicker.show()
        }

        val datePicker = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            date = dateToMilliseconds(dayOfMonth, monthOfYear, year)
            viewModel.setPathDateValue(date)
        }, currentYear, currentMoth, currentDay)

        binding.pathDate.setOnClickListener {
            datePicker.updateDate(currentYear, currentMoth, currentDay)
            datePicker.show()
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            difficulty = getDifficulty(group.checkedChipId)
        }

        binding.doneButton.setOnClickListener {
            title = binding.pathTitle.text.toString()
            topic = binding.pathTopic.text.toString()
            description = binding.pathDescription.text.toString()

            val path = Path(
                id = roadId,
                title = title,
                topic = topic,
                description = description,
                durationInMilliseconds = duration,
                dateInMilliseconds = date,
                difficulty = difficulty
            )
            viewModel.updatePath(path)

            findNavController().navigateUp()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun durationInMillisecondsToString(duration: Long): String{
        return DateFormat.getPatternInstance(DateFormat.HOUR24_MINUTE).format(duration)
    }

    private fun dateInMillisecondsToString(date: Long): String {
        return DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)
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

    private fun setChipDifficulty(difficulty: Difficulty) {
        when (difficulty) {
            Difficulty.VERY_EASY -> {
                binding.veryEasyDifficultyChip.isChecked = true
            }
            Difficulty.EASY -> {
                binding.easyDifficultyChip.isChecked = true
            }
            Difficulty.NORMAL -> {
                binding.normalDifficultyChip.isChecked = true
            }
            Difficulty.HARD -> {
                binding.hardDifficultyChip.isChecked = true
            }
            Difficulty.VERY_HARD -> {
                binding.veryHardDifficultyChip.isChecked = true
            }
        }
    }

    private fun getDifficulty(chipId: Int): Difficulty {
        when (chipId) {
            binding.veryEasyDifficultyChip.id -> {
                return Difficulty.VERY_EASY
            }
            binding.easyDifficultyChip.id -> {
                return Difficulty.EASY
            }
            binding.normalDifficultyChip.id -> {
                return Difficulty.NORMAL
            }
            binding.hardDifficultyChip.id -> {
                return Difficulty.HARD
            }
            binding.veryHardDifficultyChip.id -> {
                return Difficulty.VERY_HARD
            }
            else -> {
                return Difficulty.NORMAL
            }
        }
    }
}