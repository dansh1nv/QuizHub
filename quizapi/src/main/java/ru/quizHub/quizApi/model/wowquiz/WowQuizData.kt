package ru.quizHub.quizApi.model.wowquiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WowQuizData(
    @SerialName("games")
    val games: List<WowGameDTO>? = null,
    @SerialName("pageCount")
    val pageCount: Int? = null,
    @SerialName("perPage")
    val perPage: Int? = null,
)
