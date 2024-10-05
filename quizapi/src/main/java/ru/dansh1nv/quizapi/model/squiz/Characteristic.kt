package ru.dansh1nv.quizapi.model.squiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Characteristic (
    @SerialName("title")
    val title: String?,
    @SerialName("value")
    val value: String?,
)