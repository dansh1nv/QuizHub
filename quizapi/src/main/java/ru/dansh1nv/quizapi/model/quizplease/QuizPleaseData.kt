package ru.dansh1nv.quizapi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleaseData(
    @SerialName("data")
    val quizData: List<QuizPleaseDTO>? = null,
    @SerialName("count")
    val count: String? = null,
)
