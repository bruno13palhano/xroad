package com.example.xroad.ui.newpath.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Difficulty
import com.example.model.model.Path
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPathViewModel @Inject constructor(
    @SimplePathRep private val pathRepository: PathRepository
) : ViewModel() {
    private var _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private var _topic = MutableStateFlow("")
    val topic = _topic.asStateFlow()

    private var _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private var _duration = MutableStateFlow(0L)
    val duration = _duration.asStateFlow()

    private var _date = MutableStateFlow(0L)
    val date = _date.asStateFlow()

    private var _difficulty = MutableStateFlow(Difficulty.NORMAL)
    val difficulty = _difficulty.asStateFlow()

    fun insertPath(path: Path) {
        viewModelScope.launch {
            pathRepository.insert(path)
        }
    }

    fun setTitleValue(title: String) {
        _title.value = title
    }

    fun setTopicValue(topic: String) {
        _topic.value = topic
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun setDurationValue(duration: Long) {
        _duration.value = duration
    }

    fun setDateValue(date: Long) {
        _date.value = date
    }

    fun setDifficulty(difficulty: Difficulty) {
        _difficulty.value = difficulty
    }
}