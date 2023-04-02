package com.example.xroad.ui.analytics.week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.xroad.R
import com.example.xroad.databinding.FragmentAnalyticsWeekChartBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyticsWeekChartFragment : Fragment() {
    private var _binding: FragmentAnalyticsWeekChartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnalyticsWeekChartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsWeekChartBinding.inflate(inflater, container, false)
        val view = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    val chartModel: AAChartModel = AAChartModel()
                        .chartType(AAChartType.Column)
                        .title(getString(R.string.hours_label))
                        .subtitle(getString(R.string.average_hours_week_day))
                        .dataLabelsEnabled(true)
                        .colorsTheme(arrayOf(getString(R.string.january_light_blue_label)))
                        .series(
                            arrayOf(
                                AASeriesElement()
                                    .name(getString(R.string.average_hours_label))
                                    .data(
                                        arrayOf(
                                            it.sunday,
                                            it.monday,
                                            it.tuesday,
                                            it.wednesday,
                                            it.thursday,
                                            it.friday,
                                            it.saturday
                                        )
                                    )
                                    .color(getString(R.string.january_light_blue_label)),
                            )).categories(
                                arrayOf(
                                    getString(R.string.sunday_label),
                                    getString(R.string.monday_label),
                                    getString(R.string.tuesday_label),
                                    getString(R.string.wednesday_label),
                                    getString(R.string.thursday_label),
                                    getString(R.string.friday_label),
                                    getString(R.string.saturday_label)
                                )
                            )

                    binding.weekChart.aa_drawChartWithChartModel(chartModel)
                }
            }
        }

        return view
    }
}