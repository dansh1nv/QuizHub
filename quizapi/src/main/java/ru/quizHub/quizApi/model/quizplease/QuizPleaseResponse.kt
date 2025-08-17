package ru.quizHub.quizApi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.quizHub.quizApi.model.quizplease.QuizPleaseData

@Serializable
data class QuizPleaseResponse(
    @SerialName("data")
    val data: QuizPleaseData? = null,
)