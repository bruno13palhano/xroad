package com.example.xroad.ui.home

import android.icu.text.DateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.xroad.R
import com.example.xroad.databinding.FragmentHomeBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToNewPath()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        setUiState(it)
                    }
                }
                launch {
                    viewModel.charUiState.collect {durations ->
                        val chartModel: AAChartModel = AAChartModel()
                            .chartType(AAChartType.Area)
                            .title("Paths")
                            .subtitle("All paths to Expertise")
                            .dataLabelsEnabled(true)
                            .colorsTheme(arrayOf("#BB86FC"))
                            .series(
                                arrayOf(AASeriesElement()
                                    .name("Path")
                                    .data(durations.toTypedArray())
                                ))
                        binding.chartPath.aa_drawChartWithChartModel(chartModel)
                    }
                }
            }
        }

        return view
    }

    private fun setUiState(uiState: HomeUiState) {
        binding.days.text = getString(R.string.days_label, uiState.days)
        binding.lastPathTitle.text = getString(R.string.last_path_title_label, uiState.pathTitle)
        binding.lastPathTopic.text = getString(R.string.last_path_topic_label, uiState.pathTopic)
        binding.lastPathDuration.text = getString(R.string.last_path_duration_label,
            durationInMillisecondsToString(uiState.pathDuration))
        binding.lastPathDate.text = getString(R.string.last_path_date_label,
            dateInMillisecondsToString(uiState.pathDate))
    }

    private fun durationInMillisecondsToString(duration: Long): String {
        return DateFormat.getPatternInstance(DateFormat.HOUR24_MINUTE).format(duration)
    }

    private fun dateInMillisecondsToString(date: Long): String {
        return DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)
    }
}