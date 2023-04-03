package com.example.xroad.ui.analytics

import com.example.model.model.Day
import com.example.model.model.Month
import java.util.*

fun averageDurationToDecimal(hours: List<Int>, minutes: List<Int>): Float {
    var totalHours = hours.sum()
    var totalMinute = 0

    minutes.forEach {
        totalMinute += it
        if (totalMinute >= 60) {
            totalHours++
            totalMinute -= 60
        }
    }

    val durationDecimal = "$totalHours.${totalMinute * 100 / 60}".toFloat()

    return if (durationDecimal == 0.0F) 0.0F else String
        .format("%.2f", durationDecimal/hours.size).replace(",", ".").toFloat()
}

fun minutesToInt(duration: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = duration

    return calendar.get(Calendar.MINUTE)
}

fun hoursToInt(duration: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = duration

    return calendar.get(Calendar.HOUR_OF_DAY)
}

fun whichMonth(date: Long): Month {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date

    return when (calendar.get(Calendar.MONTH)) {
        0 -> {
            Month.JANUARY
        }
        1 -> {
            Month.FEBRUARY
        }
        2 -> {
            Month.MARCH
        }
        3 -> {
            Month.APRIL
        }
        4 -> {
            Month.MAY
        }
        5 -> {
            Month.JUNE
        }
        6 -> {
            Month.JULY
        }
        7 -> {
            Month.AUGUST
        }
        8-> {
            Month.SEPTEMBER
        }
        9 -> {
            Month.OCTOBER
        }
        10 -> {
            Month.NOVEMBER
        }
        else -> {
            Month.DECEMBER
        }
    }
}

fun whichDay(date: Long): Day {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        1 -> {
            Day.SUNDAY
        }
        2 -> {
            Day.MONDAY
        }
        3 -> {
            Day.TUESDAY
        }
        4 -> {
            Day.WEDNESDAY
        }
        5 -> {
            Day.THURSDAY
        }
        6 -> {
            Day.FRIDAY
        }
        else -> {
            Day.SATURDAY
        }
    }
}