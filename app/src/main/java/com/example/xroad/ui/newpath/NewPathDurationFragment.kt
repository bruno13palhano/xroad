package com.example.xroad.ui.newpath

import android.app.TimePickerDialog
import android.icu.text.DateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathDurationBinding
import com.example.xroad.ui.newpath.viewmodel.NewPathViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewPathDurationFragment : Fragment() {
    private var _binding: FragmentNewPathDurationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewPathViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathDurationBinding.inflate(inflater, container, false)
        val view = binding.root

        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
            val durationInMillis = convertTimeToLong(hourOfDay, minute)
            binding.duration.text = durationInMillisecondsToString(durationInMillis)
            viewModel.setDurationValue(durationInMillis)
        }, hour, minute, true)

        binding.duration.setOnClickListener {
            timePicker.show()
        }

        binding.button.setOnClickListener {
            findNavController().navigate(
                NewPathDurationFragmentDirections.actionDurationToDate())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.toolbar.title = getString(R.string.duration_label)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun convertTimeToLong(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        return calendar.timeInMillis
    }

    private fun durationInMillisecondsToString(duration: Long): String {
        return DateFormat.getPatternInstance(DateFormat.HOUR24_MINUTE).format(duration)
    }
}