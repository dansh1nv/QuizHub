package ru.quizHub.quizlist.models

import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.Location

data class ShakerQuiz(
    val id: String?,
    val theme: String?,
    val packageNumber: String?,
    val description: String?,
    val shortDescription: String?,
    val status: Status?,
    val eventTime: GameDate?,
    val formatTime: String?,
    val price: Int?,
    val currency: String?,
    val minMembersCount: Int?,
    val maxMembersCount: Int?,
    val location: Location? = null,
    val image: String? = null,
    val capacityStatus: String?,
) : Quiz()