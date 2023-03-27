package com.example.xroad.ui.newpath

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathDurationBinding
import java.util.*

class NewPathDurationFragment : Fragment() {
    private var _binding: FragmentNewPathDurationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPathDurationBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.button.setOnClickListener {
            findNavController().navigate(
                NewPathDurationFragmentDirections.actionDurationToDate())
        }

        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
            binding.duration.text = "$hourOfDay:$minute"
        }, hour, minute, true)

        binding.duration.setOnClickListener {
            timePicker.show()
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
}