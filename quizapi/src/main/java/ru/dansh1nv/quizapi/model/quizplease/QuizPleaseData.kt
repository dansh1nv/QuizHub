package ru.dansh1nv.quizapi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleaseData(
    @SerialName("data")
    val quizData: List<QuizPleaseDTO>?,
    @SerialName("count")
    val count: String?,
)
