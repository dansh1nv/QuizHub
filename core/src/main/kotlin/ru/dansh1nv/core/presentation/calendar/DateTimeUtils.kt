package ru.dansh1nv.core.presentation.calendar

import com.kizitonwose.calendar.core.YearMonth
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month


fun YearMonth.displayText(short: Boolean = false): String {
    val month = if (short) month.shortName else month.fullName
    return "$month $year"
}

fun Month.displayText(short: Boolean = true): String {
    return if (short) shortName else fullName
}

fun DayOfWeek.displayText(uppercase: Boolean = false, narrow: Boolean = false): String {
    return when {
        narrow -> shortName.take(1)
        uppercase -> shortName.uppercase()
        else -> shortName
    }
}

private val Month.fullName: String
    get() = when (this) {
        Month.JANUARY -> "Январь"
        Month.FEBRUARY -> "Февраль"
        Month.MARCH -> "Март"
        Month.APRIL -> "Апрель"
        Month.MAY -> "Май"
        Month.JUNE -> "Июнь"
        Month.JULY -> "Июль"
        Month.AUGUST -> "Август"
        Month.SEPTEMBER -> "Сентябрь"
        Month.OCTOBER-> "Октябрь"
        Month.NOVEMBER -> "Ноябрь"
        Month.DECEMBER -> "Декабрь"
    }

private val Month.shortName: String
    get() = fullName.take(3) + "."

private val DayOfWeek.shortName: String
    get() = when (this) {
        DayOfWeek.MONDAY -> "Пн"
        DayOfWeek.TUESDAY -> "Вт"
        DayOfWeek.WEDNESDAY -> "Ср"
        DayOfWeek.THURSDAY -> "Чт"
        DayOfWeek.FRIDAY -> "Пт"
        DayOfWeek.SATURDAY -> "Сб"
        DayOfWeek.SUNDAY -> "Вс"
    }