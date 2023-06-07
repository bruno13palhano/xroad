package com.example.xroad.ui.analytics.month

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
import com.example.xroad.databinding.FragmentAnalyticsMonthChartBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyticsMonthChartFragment : Fragment() {
    private var _binding: FragmentAnalyticsMonthChartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnalyticsMonthChartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_analytics_month_chart, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.title = getString(R.string.month_chart_label)

        val chartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title(getString(R.string.hours_month_label))
            .titleStyle(AAStyle().color(getString(R.string.white)))
            .subtitle(getString(R.string.average_hours_month_label))
            .subtitleStyle(AAStyle().color(getString(R.string.white)))
            .dataLabelsEnabled(true)
            .backgroundColor(getString(R.string.gray_primary))
            .axesTextColor(getString(R.string.white))
            .dataLabelsStyle(AAStyle().color(getString(R.string.white)))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(getString(R.string.january_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.january_light_blue_label)),
                    AASeriesElement()
                        .name(getString(R.string.february_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.february_pink_label)),
                    AASeriesElement()
                        .name(getString(R.string.march_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.march_purple_label)),
                    AASeriesElement()
                        .name(getString(R.string.april_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.april_grass_green_label)),
                    AASeriesElement()
                        .name(getString(R.string.may_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.may_lilac_label)),
                    AASeriesElement()
                        .name(getString(R.string.june_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.june_pearl_label)),
                    AASeriesElement()
                        .name(getString(R.string.july_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.july_yellow_label)),
                    AASeriesElement()
                        .name(getString(R.string.august_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.august_orange_label)),
                    AASeriesElement()
                        .name(getString(R.string.september_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.september_bright_blue_label)),
                    AASeriesElement()
                        .name(getString(R.string.october_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.october_indigo_label)),
                    AASeriesElement()
                        .name(getString(R.string.november_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.november_gold_label)),
                    AASeriesElement()
                        .name(getString(R.string.december_label))
                        .data(arrayOf(0.0F))
                        .color(getString(R.string.december_dark_red)),
                )).categories(arrayOf(getString(R.string.average_hours_label)))

        binding.analyticsChart.aa_drawChartWithChartModel(chartModel)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    delay(200)
                    binding.analyticsChart.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(
                        arrayOf(
                            AASeriesElement()
                                .data(arrayOf(it.january)),
                            AASeriesElement()
                                .data(arrayOf(it.february)),
                            AASeriesElement()
                                .data(arrayOf(it.march)),
                            AASeriesElement()
                                .data(arrayOf(it.april)),
                            AASeriesElement()
                                .data(arrayOf(it.may)),
                            AASeriesElement()
                                .data(arrayOf(it.june)),
                            AASeriesElement()
                                .data(arrayOf(it.july)),
                            AASeriesElement()
                                .data(arrayOf(it.august)),
                            AASeriesElement()
                                .data(arrayOf(it.september)),
                            AASeriesElement()
                                .data(arrayOf(it.october)),
                            AASeriesElement()
                                .data(arrayOf(it.november)),
                            AASeriesElement()
                                .data(arrayOf(it.december)),
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