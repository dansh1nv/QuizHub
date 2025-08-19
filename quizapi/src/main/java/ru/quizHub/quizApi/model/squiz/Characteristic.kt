package ru.quizHub.quizApi.model.squiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Characteristic(
    @SerialName("title")
    val title: String? = null,
    @SerialName("value")
    val value: String? = null,
)