package com.example.xroad.ui.newpath

import android.app.TimePickerDialog
import android.icu.text.DateFormat
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentNewPathDurationBinding
import com.example.xroad.ui.newpath.viewmodel.NewPathViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.duration.collect {
                    binding.duration.text = durationInMillisecondsToString(it)
                }
            }
        }

        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            val durationInMillis = convertTimeToLong(hourOfDay, minute)
            viewModel.setDurationValue(durationInMillis)
        }, currentHour, currentMinute, true)

        binding.duration.setOnClickListener {
            timePicker.show()
        }

        binding.previousButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.nextButton.setOnClickListener {
            findNavController().navigate(
                NewPathDurationFragmentDirections.actionDurationToDate())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.new_path_toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.restore_values -> {
                        viewModel.restoreDurationValue()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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