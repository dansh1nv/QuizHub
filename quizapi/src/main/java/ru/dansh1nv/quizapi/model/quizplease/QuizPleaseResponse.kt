package ru.dansh1nv.quizapi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleaseResponse(
    @SerialName("data")
    val data: QuizPleaseData?,
)