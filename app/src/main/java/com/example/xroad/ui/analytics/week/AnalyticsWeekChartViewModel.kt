package com.example.xroad.ui.analytics.week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AnalyticsWeekChartViewModel @Inject constructor(
    @SimplePathRep pathRepository: PathRepository
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
                if (isTheDay(path.dateInMilliseconds, 1)) {
                    sundayHours.add(hoursToInt(path.durationInMilliseconds))
                    sundayMinutes.add(minutesToInt(path.durationInMilliseconds))
                } else if (isTheDay(path.dateInMilliseconds, 2)) {
                    mondayHours.add(hoursToInt(path.durationInMilliseconds))
                    mondayMinutes.add(minutesToInt(path.durationInMilliseconds))
                } else if (isTheDay(path.dateInMilliseconds, 3)) {
                    tuesdayHours.add(hoursToInt(path.durationInMilliseconds))
                    tuesdayMinutes.add(minutesToInt(path.durationInMilliseconds))
                } else if (isTheDay(path.dateInMilliseconds, 4)) {
                    wednesdayHours.add(hoursToInt(path.durationInMilliseconds))
                    wednesdayMinutes.add(minutesToInt(path.durationInMilliseconds))
                } else if (isTheDay(path.dateInMilliseconds, 5)) {
                    thursdayHours.add(hoursToInt(path.durationInMilliseconds))
                    thursdayMinutes.add(minutesToInt(path.durationInMilliseconds))
                } else if (isTheDay(path.dateInMilliseconds, 6)) {
                    fridayHours.add(hoursToInt(path.durationInMilliseconds))
                    fridayMinutes.add(minutesToInt(path.durationInMilliseconds))
                } else if (isTheDay(path.dateInMilliseconds, 7)) {
                    saturdayHours.add(hoursToInt(path.durationInMilliseconds))
                    saturdayMinutes.add(minutesToInt(path.durationInMilliseconds))
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

    private fun isTheDay(date: Long, day: Int): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val theDay = calendar.get(Calendar.DAY_OF_WEEK)

        return theDay == day
    }

    private fun averageDurationToDecimal(hours: List<Int>, minutes: List<Int>): Float {
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

    private fun hoursToInt(duration: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration

        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    private fun minutesToInt(duration: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration

        return calendar.get(Calendar.MINUTE)
    }

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