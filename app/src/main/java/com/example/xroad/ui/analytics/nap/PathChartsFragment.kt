package com.example.xroad.ui.analytics.nap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.xroad.MainActivity
import com.example.xroad.R
import com.example.xroad.databinding.FragmentPathChartsBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PathChartsFragment : Fragment() {
    private var _binding: FragmentPathChartsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PathChartsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_path_charts, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.title = getString(R.string.path_charts_label)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weekUiState.collect {
                    showWeekChart(it)
                }
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost: MenuHost = requireActivity()
        navHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.chart_toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.week_chart ->{
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.weekUiState.collect {
                                showWeekChart(it)
                            }
                        }
                        true
                    }
                    R.id.month_chart -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.monthUiState.collect {
                                showMonthChart(it)
                            }
                        }
                        true
                    }
                    R.id.difficulty_chart -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.difficultyUiState.collect {
                                showDifficultyChart(it)
                            }
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showWeekChart(weekChart: PathChartsViewModel.WeekChartUiState) {
        val chartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title(getString(R.string.hours_day_week_label))
            .titleStyle(AAStyle().color(getString(R.string.white)))
            .subtitle(getString(R.string.average_hours_week_day_label))
            .subtitleStyle(AAStyle().color(getString(R.string.white)))
            .dataLabelsEnabled(true)
            .backgroundColor(getString(R.string.chart_background_color))
            .axesTextColor(getString(R.string.white))
            .dataLabelsStyle(AAStyle().color(getString(R.string.white)))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(getString(R.string.average_hours_label))
                        .data(
                            arrayOf(
                                weekChart.sunday,
                                weekChart.monday,
                                weekChart.tuesday,
                                weekChart.wednesday,
                                weekChart.thursday,
                                weekChart.friday,
                                weekChart.saturday
                            )
                        )
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

        binding.pathCharts.aa_drawChartWithChartModel(chartModel)
    }

    private fun showMonthChart(monthChart: PathChartsViewModel.MonthChartUiState) {
        val chartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title(getString(R.string.hours_month_label))
            .titleStyle(AAStyle().color(getString(R.string.white)))
            .subtitle(getString(R.string.average_hours_month_label))
            .subtitleStyle(AAStyle().color(getString(R.string.white)))
            .dataLabelsEnabled(true)
            .backgroundColor(getString(R.string.chart_background_color))
            .axesTextColor(getString(R.string.white))
            .dataLabelsStyle(AAStyle().color(getString(R.string.white)))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(getString(R.string.january_label))
                        .data(arrayOf(monthChart.january))
                        .color(getString(R.string.january_light_blue_label)),
                    AASeriesElement()
                        .name(getString(R.string.february_label))
                        .data(arrayOf(monthChart.february))
                        .color(getString(R.string.february_pink_label)),
                    AASeriesElement()
                        .name(getString(R.string.march_label))
                        .data(arrayOf(monthChart.march))
                        .color(getString(R.string.march_purple_label)),
                    AASeriesElement()
                        .name(getString(R.string.april_label))
                        .data(arrayOf(monthChart.april))
                        .color(getString(R.string.april_grass_green_label)),
                    AASeriesElement()
                        .name(getString(R.string.may_label))
                        .data(arrayOf(monthChart.may))
                        .color(getString(R.string.may_lilac_label)),
                    AASeriesElement()
                        .name(getString(R.string.june_label))
                        .data(arrayOf(monthChart.june))
                        .color(getString(R.string.june_pearl_label)),
                    AASeriesElement()
                        .name(getString(R.string.july_label))
                        .data(arrayOf(monthChart.july))
                        .color(getString(R.string.july_yellow_label)),
                    AASeriesElement()
                        .name(getString(R.string.august_label))
                        .data(arrayOf(monthChart.august))
                        .color(getString(R.string.august_orange_label)),
                    AASeriesElement()
                        .name(getString(R.string.september_label))
                        .data(arrayOf(monthChart.september))
                        .color(getString(R.string.september_bright_blue_label)),
                    AASeriesElement()
                        .name(getString(R.string.october_label))
                        .data(arrayOf(monthChart.october))
                        .color(getString(R.string.october_indigo_label)),
                    AASeriesElement()
                        .name(getString(R.string.november_label))
                        .data(arrayOf(monthChart.november))
                        .color(getString(R.string.november_gold_label)),
                    AASeriesElement()
                        .name(getString(R.string.december_label))
                        .data(arrayOf(monthChart.december))
                        .color(getString(R.string.december_dark_red)),
                )
            ).categories(arrayOf(getString(R.string.average_hours_label)))

        binding.pathCharts.aa_drawChartWithChartModel(chartModel)
    }

    private fun showDifficultyChart(difficultyChart: PathChartsViewModel.DifficultyChartUiState) {
        val chartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title(getString(R.string.hours_difficulty_label))
            .titleStyle(AAStyle().color(getString(R.string.white)))
            .subtitle(getString(R.string.hour_difficulty_label))
            .subtitleStyle(AAStyle().color(getString(R.string.white)))
            .dataLabelsEnabled(true)
            .backgroundColor(getString(R.string.chart_background_color))
            .axesTextColor(getString(R.string.white))
            .dataLabelsStyle(AAStyle().color(getString(R.string.white)))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(getString(R.string.average_hours_label))
                        .data(
                            arrayOf(
                                difficultyChart.veryEasyAverageDuration,
                                difficultyChart.easyAverageDuration,
                                difficultyChart.normalAverageDuration,
                                difficultyChart.hardAverageDuration,
                                difficultyChart.veryHardAverageDuration
                            )
                        )
                        .color(getString(R.string.july_yellow_label))
                )
            ).categories(
                arrayOf(
                    getString(R.string.very_easy_label),
                    getString(R.string.easy_label),
                    getString(R.string.normal_label),
                    getString(R.string.hard_label),
                    getString(R.string.very_hard_label)
                )
            )

        binding.pathCharts.aa_drawChartWithChartModel(chartModel)
    }
}