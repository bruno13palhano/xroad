package com.example.xroad.ui.path.viewmodel

import android.icu.text.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Difficulty
import com.example.model.model.Path
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PathViewModel @Inject constructor(
    @SimplePathRep private val pathRep: PathRepository
) : ViewModel() {

    private var _pathDuration = MutableStateFlow(0L)
    val pathDuration = _pathDuration.asStateFlow()

    val _durationText = _pathDuration.map {
        DateFormat.getPatternInstance(DateFormat.HOUR24_MINUTE).format(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = "",
        started = WhileSubscribed(5_000)
    )

    private var _pathDate = MutableStateFlow(0L)
    val pathDate = _pathDate.asStateFlow()

    val _dateText = _pathDate.map {
        DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = "",
        started = WhileSubscribed(5_000)
    )

    fun setPathDurationValue(duration: Long) {
        _pathDuration.value = duration
    }

    fun setPathDateValue(date: Long) {
        _pathDate.value = date
    }

    val _title = MutableStateFlow("")
    val _topic = MutableStateFlow("")
    val _description = MutableStateFlow("")

    val _veryEasy = MutableStateFlow(false)
    val _easy = MutableStateFlow(false)
    val _normal = MutableStateFlow(false)
    val _hard = MutableStateFlow(false)
    val _veryHard = MutableStateFlow(false)

    val difficulty = combine(
        _veryEasy,
        _easy,
        _normal,
        _hard,
        _veryHard
    ) { veryEasy, easy, normal, hard, veryHard ->
        var difficulty = Difficulty.NORMAL
        if (veryEasy) {
            difficulty = Difficulty.VERY_EASY
        } else if(easy) {
            difficulty = Difficulty.EASY
        } else if (normal) {
            difficulty = Difficulty.NORMAL
        } else if (hard) {
            difficulty = Difficulty.HARD
        } else if (veryHard) {
            difficulty = Difficulty.VERY_HARD
        }

        difficulty
    }.stateIn(
        scope = viewModelScope,
        initialValue = Difficulty.NORMAL,
        started = WhileSubscribed(5_000)
    )

    fun getPath(id: Long) {
        viewModelScope.launch {
            pathRep.getPathByIdStream(id).collect {
                _title.value = it.title
                _topic.value = it.topic
                _description.value = it.description
                setDifficulty(it.difficulty)
                _pathDuration.value = it.durationInMilliseconds
                _pathDate.value = it.dateInMilliseconds
            }
        }
    }

    fun update(id: Long, difficulty: Difficulty) {
        viewModelScope.launch {
            val path = Path(
                id = id,
                title = _title.value,
                topic = _topic.value,
                description = _description.value,
                durationInMilliseconds = pathDuration.value,
                dateInMilliseconds = pathDate.value,
                difficulty = difficulty
            )
            pathRep.updatePath(path)
        }
    }

    private fun setDifficulty(difficulty: Difficulty) {
        when (difficulty) {
            Difficulty.VERY_EASY -> {
                _veryEasy.value = true
            }
            Difficulty.EASY -> {
                _easy.value = true
            }
            Difficulty.NORMAL -> {
                _normal.value = true
            }
            Difficulty.HARD -> {
                _hard.value = true
            }
            Difficulty.VERY_HARD -> {
                _veryHard.value = true
            }
        }
    }
}