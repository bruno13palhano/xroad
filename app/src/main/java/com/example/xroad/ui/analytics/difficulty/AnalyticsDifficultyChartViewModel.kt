package com.example.xroad.ui.analytics.difficulty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Difficulty
import com.example.xroad.ui.analytics.averageDurationToDecimal
import com.example.xroad.ui.analytics.hoursToInt
import com.example.xroad.ui.analytics.minutesToInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
                veryEasyAverageDuration = averageDurationToDecimal(veryEasyHours, veryEasyMinutes),
                easyAverageDuration = averageDurationToDecimal(easyHours, easyMinutes),
                normalAverageDuration = averageDurationToDecimal(normalHours, normalMinutes),
                hardAverageDuration = averageDurationToDecimal(hardHours, hardMinutes),
                veryHardAverageDuration = averageDurationToDecimal(veryHardHours, veryHardMinutes)
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = ChartUiState(),
            started = WhileSubscribed(5_000)
        )

    data class ChartUiState(
        val veryEasyAverageDuration: Float = 0.0F,
        val easyAverageDuration: Float = 0.0F,
        val normalAverageDuration: Float = 0.0F,
        val hardAverageDuration: Float = 0.0F,
        val veryHardAverageDuration: Float = 0.0F
    )
}