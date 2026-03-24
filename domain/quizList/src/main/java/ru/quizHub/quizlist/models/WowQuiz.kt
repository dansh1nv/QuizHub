package ru.quizHub.quizlist.models

import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.Location

data class WowQuiz(
    val id: String?,
    val title: String?,
    val theme: String?,
    val template: String?,
    val description: String?,
    val shortDescription: String?,
    val image: String?,
    val price: Int?,
    val currency: String?,
    val eventTime: GameDate?,
    val formatTime: String?,
    val location: Location?,
    val status: Status?,
    val registrationType: String?,
) : Quiz()
