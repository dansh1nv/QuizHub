package ru.dansh1nv.quiz_list_domain.models

import kotlinx.datetime.LocalDateTime

data class GameDate(
    val dateTime: LocalDateTime,
    val day: String,
    val month: String,
    val time: String,
)
