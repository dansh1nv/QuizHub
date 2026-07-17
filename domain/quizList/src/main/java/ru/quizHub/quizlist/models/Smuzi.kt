package ru.quizHub.quizlist.models

import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.Location

data class Smuzi(
    val id: String?,
    val title: String?,
    val description: String?,
    val image: String?,
    val price: Int?,
    val eventTime: GameDate?,
    val location: Location?,
    val gameTypeLabel: String?,
    val url: String?,
) : Quiz()