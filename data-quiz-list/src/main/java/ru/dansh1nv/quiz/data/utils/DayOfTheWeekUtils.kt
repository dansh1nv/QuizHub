package ru.dansh1nv.quiz.data.utils

object DayOfTheWeekUtils {

    fun formatFullDayName(day: String): String {
        return when (day.trim()) {
            "пн" -> "понедельник"
            "вт" -> "вторник"
            "ср" -> "среда"
            "чт" -> "четверг"
            "пт" -> "пятница"
            "сб" -> "суббота"
            "вс" -> "воскресенье"
            else -> ""
        }
    }

}