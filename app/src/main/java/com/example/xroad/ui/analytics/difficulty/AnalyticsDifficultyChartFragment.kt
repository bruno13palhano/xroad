package com.example.xroad.ui.analytics.difficulty

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
import com.example.xroad.R
import com.example.xroad.databinding.FragmentAnalyticsDifficultyChartBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyticsDifficultyChartFragment : Fragment() {
    private var _binding: FragmentAnalyticsDifficultyChartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnalyticsDifficultyChartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_analytics_difficulty_chart, container, false)
        val view = binding.root

        val chartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title(getString(R.string.hours_difficulty_label))
            .titleStyle(AAStyle().color(getString(R.string.white)))
            .subtitle(getString(R.string.hour_difficulty_label))
            .subtitleStyle(AAStyle().color(getString(R.string.white)))
            .dataLabelsEnabled(true)
            .backgroundColor(getString(R.string.gray_primary))
            .axesTextColor(getString(R.string.white))
            .dataLabelsStyle(AAStyle().color(getString(R.string.white)))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(getString(R.string.average_hours_label))
                        .data(arrayOf(0.0F, 0.0F, 0.0F, 0.0F, 0.0F))
                        .color(getString(R.string.july_yellow_label))
                )).categories(
                arrayOf(
                    getString(R.string.very_easy_label),
                    getString(R.string.easy_label),
                    getString(R.string.normal_label),
                    getString(R.string.hard_label),
                    getString(R.string.very_hard_label)
                )
            )

        binding.difficultyChart.aa_drawChartWithChartModel(chartModel)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    delay(200)
                    binding.difficultyChart.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(
                        arrayOf(
                            AASeriesElement()
                                .data(
                                    arrayOf(
                                        it.veryEasyAverageDuration,
                                        it.easyAverageDuration,
                                        it.normalAverageDuration,
                                        it.hardAverageDuration,
                                        it.veryHardAverageDuration
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