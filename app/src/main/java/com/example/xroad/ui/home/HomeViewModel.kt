package com.example.xroad.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @SimplePathRep private val pathRepository: PathRepository
) : ViewModel() {

    val uiState = combine(
        pathRepository.getPathCountStream(),
        pathRepository.getLastPathStream()
    ) { days, lastPath ->
        HomeUiState(
            days = days,
            pathTitle = lastPath.title,
            pathTopic = lastPath.topic,
            pathDuration = lastPath.durationInMilliseconds,
            pathDate = lastPath.dateInMilliseconds
        )
    }
        .stateIn(
            scope = viewModelScope,
            initialValue = HomeUiState(),
            started = WhileSubscribed(5_000)
        )

    val charUiState = pathRepository.getAllStream().map { paths ->
        paths.map {
            durationToFloat(it.durationInMilliseconds)
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = WhileSubscribed(5_000)
    )

    private fun durationToFloat(duration: Long): Float {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = duration
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        return "$hour.$minute".toFloat()
    }
}