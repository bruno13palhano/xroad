package com.example.xroad.ui.path.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Path
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PathViewModel @Inject constructor(
    @SimplePathRep private val pathRep: PathRepository
) : ViewModel() {

    private var _pathDuration = MutableStateFlow(0L)
    val pathDuration = _pathDuration.asStateFlow()

    private var _pathDate = MutableStateFlow(0L)
    val pathDate = _pathDate.asStateFlow()

    fun setPathDurationValue(duration: Long) {
        _pathDuration.value = duration
    }

    fun setPathDateValue(date: Long) {
        _pathDate.value = date
    }

    fun getPath(id: Long): Flow<Path> {
        return pathRep.getPathByIdStream(id)
    }

    fun updatePath(path: Path) {
        viewModelScope.launch {
            pathRep.updatePath(path)
        }
    }
}