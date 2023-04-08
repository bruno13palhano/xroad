package com.example.xroad.ui.newpath.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.SimplePathRep
import com.example.core.repository.PathRepository
import com.example.model.model.Difficulty
import com.example.model.model.Path
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NewPathViewModel @Inject constructor(
    @SimplePathRep private val pathRepository: PathRepository
) : ViewModel() {
    private val currentDate = Calendar.getInstance()
    private val currentHour = currentDate.get(Calendar.HOUR_OF_DAY)
    private val currentMinute = currentDate.get(Calendar.MINUTE)
    private val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
    private val currentMonth = currentDate.get(Calendar.MONTH)
    private val currentYear = currentDate.get(Calendar.YEAR)

    val _hour = MutableStateFlow(currentHour)
    val _minute = MutableStateFlow(currentMinute)
    val _day = MutableStateFlow(currentDay)
    val _month = MutableStateFlow(currentMonth)
    val _year = MutableStateFlow(currentYear)

    val duration = combine(_hour, _minute) { hour, minute ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        calendar.timeInMillis
    }
        .stateIn(
            scope = viewModelScope,
            initialValue = 0L,
            started = WhileSubscribed(5_000)
        )

    val date = combine(_day, _month, _year) { day, month, year ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        calendar.timeInMillis
    }
        .stateIn(
            scope = viewModelScope,
            initialValue = 0L,
            started = WhileSubscribed(5_000)
        )

    val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    val _topic = MutableStateFlow("")
    val topic = _topic.asStateFlow()

    val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    val _veryEasy = MutableStateFlow(false)
    val _easy = MutableStateFlow(false)
    val _normal = MutableStateFlow(true)
    val _hard = MutableStateFlow(false)
    val _veryHard = MutableStateFlow(false)

    private val _difficulty = MutableStateFlow(Difficulty.NORMAL)
    val difficulty = _difficulty.asStateFlow()

    fun insertPath(path: Path) {
        viewModelScope.launch {
            pathRepository.insert(path)
        }
    }

    fun restorePathValues() {
        restoreTitleAndTopicValue()
        restoreDescriptionValue()
        restoreDurationValue()
        restoreDateValue()
        restoreDifficultyValue()
    }

    fun restoreTitleAndTopicValue() {
        _title.value = ""
        _topic.value = ""
    }

    fun restoreDescriptionValue() {
        _description.value = ""
    }

    fun restoreDurationValue() {
        _minute.value = currentMinute
        _hour.value = currentHour
    }

    fun restoreDateValue() {
        _day.value = currentDay
        _month.value = currentMonth
        _year.value = currentYear
    }

    fun restoreDifficultyValue() {
        _normal.value = true
    }
}