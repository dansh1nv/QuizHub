package ru.quizHub.quizApi.model.wowquiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WowQuizResponse(
    @SerialName("data")
    val data: WowQuizData? = null,
)
