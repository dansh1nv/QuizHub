package ru.dansh1nv.quiz.list.models.item

import kotlinx.datetime.LocalDateTime

data class GameDateUI(
    val date: LocalDateTime?,
    val dateText: String,
    val timeWithDay: String,
)