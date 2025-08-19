package ru.quizHub.quizlist.models.common

import kotlinx.datetime.LocalDateTime

data class GameDate(
    val dateTime: LocalDateTime,
    val day: String,
    val month: String,
    val time: String,
)
