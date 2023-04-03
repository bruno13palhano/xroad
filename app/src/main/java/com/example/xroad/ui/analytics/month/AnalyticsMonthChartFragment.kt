package com.example.xroad.ui.analytics.month

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
import com.example.xroad.databinding.FragmentAnalyticsMonthChartBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import dagger.hilt.android.AndroidEntryPoint
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
        _binding = FragmentAnalyticsMonthChartBinding.inflate(inflater, container, false)
        val view = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
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
                                    .data(arrayOf(it.january))
                                    .color(getString(R.string.january_light_blue_label)),
                                AASeriesElement()
                                    .name(getString(R.string.february_label))
                                    .data(arrayOf(it.february))
                                    .color(getString(R.string.february_pink_label)),
                                AASeriesElement()
                                    .name(getString(R.string.march_label))
                                    .data(arrayOf(it.march))
                                    .color(getString(R.string.march_purple_label)),
                                AASeriesElement()
                                    .name(getString(R.string.april_label))
                                    .data(arrayOf(it.april))
                                    .color(getString(R.string.april_grass_green_label)),
                                AASeriesElement()
                                    .name(getString(R.string.may_label))
                                    .data(arrayOf(it.may))
                                    .color(getString(R.string.may_lilac_label)),
                                AASeriesElement()
                                    .name(getString(R.string.june_label))
                                    .data(arrayOf(it.june))
                                    .color(getString(R.string.june_pearl_label)),
                                AASeriesElement()
                                    .name(getString(R.string.july_label))
                                    .data(arrayOf(it.july))
                                    .color(getString(R.string.july_yellow_label)),
                                AASeriesElement()
                                    .name(getString(R.string.august_label))
                                    .data(arrayOf(it.august))
                                    .color(getString(R.string.august_orange_label)),
                                AASeriesElement()
                                    .name(getString(R.string.september_label))
                                    .data(arrayOf(it.september))
                                    .color(getString(R.string.september_bright_blue_label)),
                                AASeriesElement()
                                    .name(getString(R.string.october_label))
                                    .data(arrayOf(it.october))
                                    .color(getString(R.string.october_indigo_label)),
                                AASeriesElement()
                                    .name(getString(R.string.november_label))
                                    .data(arrayOf(it.november))
                                    .color(getString(R.string.november_gold_label)),
                                AASeriesElement()
                                    .name(getString(R.string.december_label))
                                    .data(arrayOf(it.december))
                                    .color(getString(R.string.december_dark_red)),
                            )).categories(
                            arrayOf(getString(R.string.average_hours_label))
                        )

                    binding.analyticsChart.aa_drawChartWithChartModel(chartModel)
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