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

object MonthConverter {
    fun getMonthByName(monthName: String): Int {
        return when(monthName) {
            "января" -> 1
            "февраля" -> 2
            "марта" -> 3
            "апреля" -> 4
            "мая" -> 5
            "июня" -> 6
            "июля" -> 7
            "августа" -> 8
            "сентября" -> 9
            "октября" -> 10
            "ноября" -> 11
            else -> 12
        }
    }

    fun getMonthNameByNumber(number: Int): String {
        return when(number) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            else -> "декабря"
        }
    }
}