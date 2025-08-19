package ru.quizHub.quizApi.model.squiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Products(
    @SerialName("products")
    val quizGames: List<SquizDTO>? = null,
)