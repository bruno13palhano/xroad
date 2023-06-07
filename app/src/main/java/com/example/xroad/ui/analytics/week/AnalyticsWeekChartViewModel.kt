package com.example.xroad.ui.analytics.week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Day
import com.example.xroad.ui.analytics.averageDurationToDecimal
import com.example.xroad.ui.analytics.hoursToInt
import com.example.xroad.ui.analytics.minutesToInt
import com.example.xroad.ui.analytics.whichDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnalyticsWeekChartViewModel @Inject constructor(
    @SimplePathRep private val pathRepository: PathRepository
) : ViewModel() {

    val uiState = pathRepository.getAllStream()
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

            ChartUiState(
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
                initialValue = ChartUiState(),
                started = WhileSubscribed(5_000)
            )

    data class ChartUiState(
        val monday: Float = 0.0F,
        val sunday: Float = 0.0F,
        val tuesday: Float = 0.0F,
        val wednesday: Float = 0.0F,
        val thursday: Float = 0.0F,
        val friday: Float = 0.0F,
        val saturday: Float = 0.0F
    )
}