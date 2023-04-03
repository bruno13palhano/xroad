package com.example.xroad.ui.analytics.difficulty

import androidx.lifecycle.ViewModel
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Difficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AnalyticsDifficultyChartViewModel @Inject constructor(
    @SimplePathRep pathRepository: PathRepository
) : ViewModel() {

    val uiState = pathRepository.getAllStream()
        .map {
            val veryEasyHours = mutableListOf<Int>()
            val veryEasyMinutes = mutableListOf<Int>()
            val easyHours = mutableListOf<Int>()
            val easyMinutes = mutableListOf<Int>()
            val normalHours = mutableListOf<Int>()
            val normalMinutes = mutableListOf<Int>()
            val hardHours = mutableListOf<Int>()
            val hardMinutes = mutableListOf<Int>()
            val veryHardHours = mutableListOf<Int>()
            val veryHardMinutes = mutableListOf<Int>()

            it.forEach { path ->
                when (path.difficulty) {
                    Difficulty.VERY_EASY -> {
                        veryEasyHours.add(hoursToInt(path.durationInMilliseconds))
                        veryEasyMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Difficulty.EASY -> {
                        easyHours.add(hoursToInt(path.durationInMilliseconds))
                        easyMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Difficulty.NORMAL -> {
                        normalHours.add(hoursToInt(path.durationInMilliseconds))
                        normalMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Difficulty.HARD -> {
                        hardHours.add(hoursToInt(path.durationInMilliseconds))
                        hardMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Difficulty.VERY_HARD -> {
                        veryHardHours.add(hoursToInt(path.durationInMilliseconds))
                        veryHardMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                }
            }

            ChartUiState(
                veryEasyAverageDuration = averageDurationToDecimal(veryEasyMinutes, veryEasyHours),
                easyAverageDuration = averageDurationToDecimal(easyMinutes, easyHours),
                normalAverageDuration = averageDurationToDecimal(normalMinutes, normalHours),
                hardAverageDuration = averageDurationToDecimal(hardMinutes, hardHours),
                veryHardAverageDuration = averageDurationToDecimal(veryHardMinutes, veryHardHours)
            )
        }

    private fun averageDurationToDecimal(minutes: List<Int>, hours: List<Int>): Float {
        var totalHours = hours.sum()
        var totalMinute = 0

        minutes.forEach {
            totalMinute += it
            if (totalMinute >= 60) {
                totalHours++
                totalMinute -= 60
            }
        }

        val durationDecimal = "$totalHours.${totalMinute * 100 / 60}".toFloat()

        return if (durationDecimal == 0.0F) 0.0F else String
            .format("%.2f", durationDecimal/hours.size).replace(",", ".").toFloat()
    }

    private fun minutesToInt(duration: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration

        return calendar.get(Calendar.MINUTE)
    }

    private fun hoursToInt(duration: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration

        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    data class ChartUiState(
        val veryEasyAverageDuration: Float = 0.0F,
        val easyAverageDuration: Float = 0.0F,
        val normalAverageDuration: Float = 0.0F,
        val hardAverageDuration: Float = 0.0F,
        val veryHardAverageDuration: Float = 0.0F
    )
}