package ru.dansh1nv.quiz.list.models

import java.time.LocalDate

data class GameDateUI(
    val date: LocalDate,
    val dateText: String,
    val timeWithDay: String,
)