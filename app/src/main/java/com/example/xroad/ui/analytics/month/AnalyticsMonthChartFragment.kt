package com.example.xroad.ui.analytics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xroad.R
import com.example.xroad.databinding.FragmentAnalyticsChartBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

class AnalyticsChartFragment : Fragment() {
    private var _binding: FragmentAnalyticsChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsChartBinding.inflate(inflater, container, false)
        val view = binding.root

        val chartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Bar)
            .title(getString(R.string.paths_label))
            .subtitle(getString(R.string.paths_expertise))
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#BB86FC"))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name(getString(R.string.january_label))
                        .data(arrayOf(3.3, 4.7, 2.2, 10.2, 4.4, 5.1, 12.2))
                        .color(getString(R.string.january_light_blue_label)),
                    AASeriesElement()
                        .name(getString(R.string.february_label))
                        .data(arrayOf(1.3, 4.2, 5.2, 4.2, 4.4, 5.1, 8.2))
                        .color(getString(R.string.february_pink_label)),
                    AASeriesElement()
                        .name(getString(R.string.march_label))
                        .data(arrayOf(6.3, 4.7, 5.7, 11.2, 8.9, 12.2, 11.2))
                        .color(getString(R.string.march_purple_label)),
                    AASeriesElement()
                        .name(getString(R.string.april_label))
                        .data(arrayOf(7.3, 5.8, 7.7, 9.3, 8.4, 12.5, 11.2))
                        .color(getString(R.string.april_grass_green_label)),
                    AASeriesElement()
                        .name(getString(R.string.may_label))
                        .data(arrayOf(2.3, 2.7, 2.2, 2.4, 2.9, 4.1, 7.1))
                        .color(getString(R.string.may_lilac_label)),
                    AASeriesElement()
                        .name(getString(R.string.june_label))
                        .data(arrayOf(4.3, 4.1, 7.3, 8.3, 4.9, 7.1, 9.2))
                        .color(getString(R.string.june_pearl_label)),
                    AASeriesElement()
                        .name(getString(R.string.july_label))
                        .data(arrayOf(12.3, 10.7, 8.2, 5.2, 14.4, 9.1, 11.2))
                        .color(getString(R.string.july_yellow_label)),
                    AASeriesElement()
                        .name(getString(R.string.august_label))
                        .data(arrayOf(7.3, 6.4, 5.4, 7.2, 6.4, 5.1, 8.2))
                        .color(getString(R.string.august_orange_label)),
                    AASeriesElement()
                        .name(getString(R.string.september_label))
                        .data(arrayOf(2.3, 1.7, 1.8, 2.3, 3.3, 2.1, 2.2))
                        .color(getString(R.string.september_bright_blue_label)),
                    AASeriesElement()
                        .name(getString(R.string.october_label))
                        .data(arrayOf(1.3, 4.2, 5.2, 4.2, 4.4, 5.1, 8.2))
                        .color(getString(R.string.october_indigo_label)),
                    AASeriesElement()
                        .name(getString(R.string.november_label))
                        .data(arrayOf(7.2, 6.7, 8.9, 10.1, 10.4, 9.5, 9.2))
                        .color(getString(R.string.november_gold_label)),
                    AASeriesElement()
                        .name(getString(R.string.december_label))
                        .data(arrayOf(7.3, 1.2, 2.2, 3.2, 1.4, 5.1, 3.2))
                        .color(getString(R.string.december_dark_red)),
                )).categories(
                    arrayOf(
                        getString(R.string.monday_label),
                        getString(R.string.tuesday_label),
                        getString(R.string.wednesday_label),
                        getString(R.string.thursday_label),
                        getString(R.string.friday_label),
                        getString(R.string.saturday_label),
                        getString(R.string.sunday_label)
                    )
                )

        binding.analyticsChart.aa_drawChartWithChartModel(chartModel)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}