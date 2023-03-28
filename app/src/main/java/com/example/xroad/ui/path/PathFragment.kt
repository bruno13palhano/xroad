package com.example.xroad.ui.path

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
import com.example.xroad.R
import com.example.xroad.databinding.FragmentPathBinding
import com.example.xroad.ui.path.viewmodel.PathViewModel
import com.google.android.material.appbar.MaterialToolbar
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

        val roadId = PathFragmentArgs.fromBundle(requireArguments()).roadId

        val currentTime = Calendar.getInstance()
        var currentHour = 0
        var currentMinute = 0

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPath(roadId).collect {
                    binding.pathTitle.setText(it.title)
                    binding.pathTopic.setText(it.topic)
                    binding.pathDescription.setText(it.description)
                    binding.pathDuration
                        .setText(durationInMillisecondsToString(it.durationInMilliseconds))
                    binding.pathDate.setText(dateInMillisecondsToString(it.dateInMilliseconds))

                    currentTime.timeInMillis = it.durationInMilliseconds
                    currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
                    currentMinute = currentTime.get(Calendar.MINUTE)
                }
            }
        }


        val timePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->

        }, currentHour, currentMinute, true)

        binding.pathDuration.setOnClickListener {
            timePicker.show()
        }

        binding.pathDate.setOnClickListener {

        }

        binding.doneButton.setOnClickListener {
            // TODO: implementar o path update
            findNavController().navigateUp()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.title = getString(R.string.path_label)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun durationInMillisecondsToString(duration: Long): String{
        return DateFormat.getPatternInstance(DateFormat.HOUR24_MINUTE).format(duration)
    }

    private fun dateInMillisecondsToString(date: Long): String {
        return DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)
    }
}