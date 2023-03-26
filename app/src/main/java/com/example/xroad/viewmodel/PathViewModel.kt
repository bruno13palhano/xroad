package com.example.xroad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Path
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoadViewModel @Inject constructor(
    @SimplePathRep private val roadRepository: PathRepository
) : ViewModel() {
    private var _allPaths = MutableStateFlow<List<Path>>(emptyList())
    val allPaths = _allPaths.asStateFlow()

    init {
        viewModelScope.launch {
            roadRepository.getAll().collect {
                _allPaths.value = it
            }
        }
    }

    fun insertProductInDB(path: Path) {
        viewModelScope.launch {
            roadRepository.insert(path)
        }
    }
}