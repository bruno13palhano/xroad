package com.example.xroad.ui.analytics.month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Month
import com.example.xroad.ui.analytics.averageDurationToDecimal
import com.example.xroad.ui.analytics.hoursToInt
import com.example.xroad.ui.analytics.minutesToInt
import com.example.xroad.ui.analytics.whichMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnalyticsMonthChartViewModel @Inject constructor(
    @SimplePathRep private val pathRepository: PathRepository
) : ViewModel() {

    val uiState = pathRepository.getAllStream()
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

            ChartUiState(
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
            initialValue = ChartUiState(),
            started = WhileSubscribed(5_000)
        )

    data class ChartUiState(
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
}