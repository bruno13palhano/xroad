package com.example.xroad.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
}