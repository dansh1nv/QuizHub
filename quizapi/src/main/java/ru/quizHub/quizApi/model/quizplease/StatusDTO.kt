package ru.quizHub.quizApi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusDTO(
    @SerialName("id")
    val id: Int? = null,
)