package ru.quizHub.quizList.models.item

import androidx.compose.ui.graphics.Color

data class CalendarEventUI(
    val color: Color,
    val startAngle: Float,
    val sweepAngle: Float,
    val formattedDate: GameDateUI?
)