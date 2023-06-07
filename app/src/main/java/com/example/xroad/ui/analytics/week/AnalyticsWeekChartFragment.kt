package com.example.xroad.ui.analytics.week

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
import com.example.xroad.MainActivity
import com.example.xroad.R
import com.example.xroad.databinding.FragmentAnalyticsWeekChartBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_analytics_week_chart, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.title = getString(R.string.week_chart_label)

        val chartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title(getString(R.string.hours_day_week_label))
            .titleStyle(AAStyle().color(getString(R.string.white)))
            .subtitle(getString(R.string.average_hours_week_day_label))
            .subtitleStyle(AAStyle().color(getString(R.string.white)))
            .dataLabelsEnabled(true)
            .backgroundColor(getString(R.string.gray_primary))
            .axesTextColor(getString(R.string.white))
            .dataLabelsStyle(AAStyle().color(getString(R.string.white)))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(getString(R.string.average_hours_label))
                        .data(arrayOf(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F))
                        .color(getString(R.string.july_yellow_label)),
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    delay(200)
                    binding.weekChart.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(
                        arrayOf(
                            AASeriesElement()
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
                        )
                    )
                }
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}