package ru.dansh1nv.quiz_list_domain.models

import java.time.LocalDate

data class GameDate(
    val localDate: LocalDate,
    val day: String,
    val month: String,
    val dayOfTheWeek: String,
    val time: String,
    val dayWithMonth: String,
)
