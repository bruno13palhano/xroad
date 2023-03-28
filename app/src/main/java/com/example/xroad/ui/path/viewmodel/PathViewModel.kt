package com.example.xroad.ui.path.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Path
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PathViewModel @Inject constructor(
    @SimplePathRep private val pathRep: PathRepository
) : ViewModel() {

    fun getPath(id: Long): Flow<Path> {
        return pathRep.getPathById(id)
    }
}