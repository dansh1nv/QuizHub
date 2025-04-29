package ru.dansh1nv.core.presentation.calendar

import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun YearMonth.displayText(
    short: Boolean = false,
    locate: Locale = Locale("ru", "RU")
): String {
    val pattern = if (short) "LLL yyyy" else "LLLL yyyy"
    val formatter = DateTimeFormatter
        .ofPattern(pattern, locate)
    return this.format(formatter)
        .replaceFirstChar { it.uppercase() }
}

fun Month.displayText(
    short: Boolean = true,
    locale: Locale = Locale("ru", "RU")
): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, locale)
}

fun DayOfWeek.displayText(
    uppercase: Boolean = false,
    narrow: Boolean = false,
    locale: Locale = Locale("ru", "RU")
): String {
    val style = if (narrow) TextStyle.NARROW else TextStyle.SHORT
    return getDisplayName(style, locale).let { value ->
        if (uppercase) value.uppercase(locale) else value
    }
}