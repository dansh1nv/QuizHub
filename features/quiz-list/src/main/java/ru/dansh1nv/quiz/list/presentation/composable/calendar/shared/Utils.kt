package ru.dansh1nv.quiz.list.presentation.composable.calendar.shared

import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun YearMonth.displayText(short: Boolean = false): String {
    val formatter = DateTimeFormatter
        .ofPattern("LLLL yyyy", Locale("ru", "RU"))
    return this.format(formatter)
        .replaceFirstChar { it.uppercase() }
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale("ru", "RU"))
}

fun DayOfWeek.displayText(uppercase: Boolean = false, narrow: Boolean = false): String {
    val style = if (narrow) TextStyle.NARROW else TextStyle.SHORT
    return getDisplayName(style, Locale("ru", "RU")).let { value ->
        if (uppercase) value.uppercase(Locale("ru", "RU")) else value
    }
}