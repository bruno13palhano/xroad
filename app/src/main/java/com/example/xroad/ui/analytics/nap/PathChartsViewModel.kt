package com.example.xroad.ui.analytics.nap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Day
import com.example.model.model.Difficulty
import com.example.model.model.Month
import com.example.xroad.ui.analytics.averageDurationToDecimal
import com.example.xroad.ui.analytics.hoursToInt
import com.example.xroad.ui.analytics.minutesToInt
import com.example.xroad.ui.analytics.whichDay
import com.example.xroad.ui.analytics.whichMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PathChartsViewModel @Inject constructor(
    @SimplePathRep private val pathRepository: PathRepository
) : ViewModel() {

    val weekUiState = pathRepository.getAllStream()
        .map {
            val sundayMinutes = mutableListOf<Int>()
            val sundayHours = mutableListOf<Int>()
            val mondayMinutes = mutableListOf<Int>()
            val mondayHours = mutableListOf<Int>()
            val tuesdayMinutes = mutableListOf<Int>()
            val tuesdayHours = mutableListOf<Int>()
            val wednesdayMinutes = mutableListOf<Int>()
            val wednesdayHours = mutableListOf<Int>()
            val thursdayMinutes = mutableListOf<Int>()
            val thursdayHours = mutableListOf<Int>()
            val fridayMinutes = mutableListOf<Int>()
            val fridayHours = mutableListOf<Int>()
            val saturdayMinutes = mutableListOf<Int>()
            val saturdayHours = mutableListOf<Int>()

            it.forEach { path ->
                when (whichDay(path.dateInMilliseconds)) {
                    Day.SUNDAY -> {
                        sundayHours.add(hoursToInt(path.durationInMilliseconds))
                        sundayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Day.MONDAY -> {
                        mondayHours.add(hoursToInt(path.durationInMilliseconds))
                        mondayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Day.TUESDAY -> {
                        tuesdayHours.add(hoursToInt(path.durationInMilliseconds))
                        tuesdayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Day.WEDNESDAY -> {
                        wednesdayHours.add(hoursToInt(path.durationInMilliseconds))
                        wednesdayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Day.THURSDAY -> {
                        thursdayHours.add(hoursToInt(path.durationInMilliseconds))
                        thursdayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Day.FRIDAY -> {
                        fridayHours.add(hoursToInt(path.durationInMilliseconds))
                        fridayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Day.SATURDAY -> {
                        saturdayHours.add(hoursToInt(path.durationInMilliseconds))
                        saturdayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                }
            }

            WeekChartUiState(
                sunday = averageDurationToDecimal(sundayHours, sundayMinutes),
                monday = averageDurationToDecimal(mondayHours, mondayMinutes),
                tuesday = averageDurationToDecimal(tuesdayHours, tuesdayMinutes),
                wednesday = averageDurationToDecimal(wednesdayHours, wednesdayMinutes),
                thursday = averageDurationToDecimal(thursdayHours, thursdayMinutes),
                friday = averageDurationToDecimal(fridayHours, fridayMinutes),
                saturday = averageDurationToDecimal(saturdayHours, saturdayMinutes)
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = WeekChartUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    data class WeekChartUiState(
        val monday: Float = 0.0F,
        val sunday: Float = 0.0F,
        val tuesday: Float = 0.0F,
        val wednesday: Float = 0.0F,
        val thursday: Float = 0.0F,
        val friday: Float = 0.0F,
        val saturday: Float = 0.0F
    )

    val monthUiState = pathRepository.getAllStream()
        .map {
            val januaryHours = mutableListOf<Int>()
            val januaryMinutes = mutableListOf<Int>()
            val februaryHours = mutableListOf<Int>()
            val februaryMinutes = mutableListOf<Int>()
            val marchHours = mutableListOf<Int>()
            val marchMinutes = mutableListOf<Int>()
            val aprilHours = mutableListOf<Int>()
            val aprilMinutes = mutableListOf<Int>()
            val mayHours = mutableListOf<Int>()
            val mayMinutes = mutableListOf<Int>()
            val juneHours = mutableListOf<Int>()
            val juneMinutes = mutableListOf<Int>()
            val julyHours = mutableListOf<Int>()
            val julyMinutes = mutableListOf<Int>()
            val augustHours = mutableListOf<Int>()
            val augustMinutes = mutableListOf<Int>()
            val septemberHours = mutableListOf<Int>()
            val septemberMinutes = mutableListOf<Int>()
            val octoberHours = mutableListOf<Int>()
            val octoberMinutes = mutableListOf<Int>()
            val novemberHours = mutableListOf<Int>()
            val novemberMinutes = mutableListOf<Int>()
            val decemberHours = mutableListOf<Int>()
            val decemberMinutes = mutableListOf<Int>()

            it.forEach { path ->
                when (whichMonth(path.dateInMilliseconds)) {
                    Month.JANUARY -> {
                        januaryHours.add(hoursToInt(path.durationInMilliseconds))
                        januaryMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.FEBRUARY -> {
                        februaryHours.add(hoursToInt(path.durationInMilliseconds))
                        februaryMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.MARCH -> {
                        marchHours.add(hoursToInt(path.durationInMilliseconds))
                        marchMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.APRIL -> {
                        aprilHours.add(hoursToInt(path.durationInMilliseconds))
                        aprilMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.MAY -> {
                        mayHours.add(hoursToInt(path.durationInMilliseconds))
                        mayMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.JUNE-> {
                        juneHours.add(hoursToInt(path.durationInMilliseconds))
                        juneMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.JULY -> {
                        julyHours.add(hoursToInt(path.durationInMilliseconds))
                        julyMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.AUGUST -> {
                        augustHours.add(hoursToInt(path.durationInMilliseconds))
                        augustMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.SEPTEMBER -> {
                        septemberHours.add(hoursToInt(path.durationInMilliseconds))
                        septemberMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.OCTOBER -> {
                        octoberHours.add(hoursToInt(path.durationInMilliseconds))
                        octoberMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.NOVEMBER -> {
                        novemberHours.add(hoursToInt(path.durationInMilliseconds))
                        novemberMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                    Month.DECEMBER -> {
                        decemberHours.add(hoursToInt(path.durationInMilliseconds))
                        decemberMinutes.add(minutesToInt(path.durationInMilliseconds))
                    }
                }
            }

            MonthChartUiState(
                january = averageDurationToDecimal(januaryHours, januaryMinutes),
                february = averageDurationToDecimal(februaryHours, februaryMinutes),
                march = averageDurationToDecimal(marchHours, marchMinutes),
                april = averageDurationToDecimal(aprilHours, aprilMinutes),
                may = averageDurationToDecimal(mayHours, mayMinutes),
                june = averageDurationToDecimal(juneHours, juneMinutes),
                july = averageDurationToDecimal(julyHours, julyMinutes),
                august = averageDurationToDecimal(augustHours, augustMinutes),
                september = averageDurationToDecimal(septemberHours, septemberMinutes),
                october = averageDurationToDecimal(octoberHours, octoberMinutes),
                november = averageDurationToDecimal(novemberHours, novemberMinutes),
                december = averageDurationToDecimal(decemberHours, decemberMinutes)
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = MonthChartUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    data class MonthChartUiState(
        val january: Float = 0.0F,
        val february: Float = 0.0F,
        val march: Float = 0.0F,
        val april: Float = 0.0F,
        val may: Float = 0.0F,
        val june : Float = 0.0F,
        val july: Float = 0.0F,
        val august: Float = 0.0F,
        val september: Float = 0.0F,
        val october: Float = 0.0F,
        val november: Float = 0.0F,
        val december: Float = 0.0F
    )

    val difficultyUiState = pathRepository.getAllStream()
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

            DifficultyChartUiState(
                veryEasyAverageDuration = averageDurationToDecimal(veryEasyHours, veryEasyMinutes),
                easyAverageDuration = averageDurationToDecimal(easyHours, easyMinutes),
                normalAverageDuration = averageDurationToDecimal(normalHours, normalMinutes),
                hardAverageDuration = averageDurationToDecimal(hardHours, hardMinutes),
                veryHardAverageDuration = averageDurationToDecimal(veryHardHours, veryHardMinutes)
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = DifficultyChartUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    data class DifficultyChartUiState(
        val veryEasyAverageDuration: Float = 0.0F,
        val easyAverageDuration: Float = 0.0F,
        val normalAverageDuration: Float = 0.0F,
        val hardAverageDuration: Float = 0.0F,
        val veryHardAverageDuration: Float = 0.0F
    )
}