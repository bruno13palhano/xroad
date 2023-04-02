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
            val sunday = it.filter { path ->
                isTheDay(path.dateInMilliseconds, 1)
            }.map { path ->
                durationToFloat(path.durationInMilliseconds)
            }

            val monday = it.filter { path ->
                isTheDay(path.dateInMilliseconds, 2)
            }.map { path ->
                durationToFloat(path.durationInMilliseconds)
            }

            val tuesday = it.filter { path ->
                isTheDay(path.dateInMilliseconds, 3)
            }.map { path ->
                durationToFloat(path.durationInMilliseconds)
            }

            val wednesday = it.filter { path ->
                isTheDay(path.dateInMilliseconds, 4)
            }.map { path ->
                durationToFloat(path.durationInMilliseconds)
            }

            val thursday = it.filter { path ->
                isTheDay(path.dateInMilliseconds, 5)
            }.map { path ->
                durationToFloat(path.durationInMilliseconds)
            }

            val friday = it.filter { path ->
                isTheDay(path.dateInMilliseconds, 6)
            }.map {  path ->
                durationToFloat(path.durationInMilliseconds)
            }

            val saturday = it.filter { path ->
                isTheDay(path.dateInMilliseconds, 7)
            }.map { path ->
                durationToFloat(path.durationInMilliseconds)
            }

            ChartUiState(
                sunday = validateDayList(sunday),
                monday = validateDayList(monday),
                tuesday = validateDayList(tuesday),
                wednesday = validateDayList(wednesday),
                thursday = validateDayList(thursday),
                friday = validateDayList(friday),
                saturday = validateDayList(saturday)
            )
        }
            .stateIn(
                scope = viewModelScope,
                initialValue = ChartUiState(),
                started = WhileSubscribed(5_000)
            )

    private fun validateDayList(day: List<Float>): Float {
        return if (day.sum() == 0.0F) 0.0F else {
            "%.2f".format(day.sum()/day.size).toFloat()
        }
    }

    private fun isTheDay(date: Long, day: Int): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val theDay = calendar.get(Calendar.DAY_OF_WEEK)

        return theDay == day
    }

    private fun durationToFloat(duration: Long): Float {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE) * 100/60

        return "$hour.$minute".toFloat()
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