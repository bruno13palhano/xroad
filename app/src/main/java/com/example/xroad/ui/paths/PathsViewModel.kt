package com.example.xroad.ui.paths

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PathsViewModel @Inject constructor(
    @SimplePathRep private val pathRepository: PathRepository
): ViewModel() {

    val uiState = pathRepository.getAllStream()
        .stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = WhileSubscribed(5_000)
        )
}