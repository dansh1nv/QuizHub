package ru.quizHub.quizList.cache

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CachedQuizPayload {
    abstract val id: String?

    @Serializable
    @SerialName("QuizPlease")
    data class QuizPlease(
        override val id: String? = null,
        val title: String? = null,
        val packageNumber: String? = null,
        val description: String? = null,
        val image: String? = null,
        val gameFormat: String? = null,
        val datetime: String? = null,
        val formatDate: CachedGameDate? = null,
        val price: Int? = null,
        val formatPrice: String? = null,
        val location: CachedLocation? = null,
        val difficulty: String? = null,
        val status: String? = null,
        val paymentMethod: String? = null,
    ) : CachedQuizPayload()

    @Serializable
    @SerialName("Squiz")
    data class Squiz(
        override val id: String? = null,
        val gameDate: CachedGameDate? = null,
        val type: String? = null,
        val format: String? = null,
        val theme: String? = null,
        val packageNumber: String? = null,
        val description: String? = null,
        val additionDescription: String? = null,
        val image: String? = null,
        val price: String? = null,
        val location: CachedLocation? = null,
        val status: String? = null,
        val difficult: String? = null,
    ) : CachedQuizPayload()

    @Serializable
    @SerialName("ShakerQuiz")
    data class Shaker(
        override val id: String? = null,
        val theme: String? = null,
        val packageNumber: String? = null,
        val description: String? = null,
        val shortDescription: String? = null,
        val status: String? = null,
        val eventTime: CachedGameDate? = null,
        val formatTime: String? = null,
        val price: Int? = null,
        val currency: String? = null,
        val minMembersCount: Int? = null,
        val maxMembersCount: Int? = null,
        val location: CachedLocation? = null,
        val image: String? = null,
        val capacityStatus: String? = null,
    ) : CachedQuizPayload()

    @Serializable
    @SerialName("RudaGames")
    data class Ruda(
        override val id: String? = null,
        val title: String? = null,
        val description: String? = null,
        val image: String? = null,
        val gameType: String? = null,
        val formatDate: CachedGameDate? = null,
        val formatTime: String? = null,
        val price: Int? = null,
        val currency: String? = null,
        val paymentMethod: String? = null,
        val location: CachedLocation? = null,
        val status: String? = null,
        val minMembersCount: Int? = null,
        val maxMembersCount: Int? = null,
    ) : CachedQuizPayload()

    @Serializable
    @SerialName("WowQuiz")
    data class Wow(
        override val id: String? = null,
        val title: String? = null,
        val theme: String? = null,
        val template: String? = null,
        val description: String? = null,
        val shortDescription: String? = null,
        val image: String? = null,
        val price: Int? = null,
        val currency: String? = null,
        val eventTime: CachedGameDate? = null,
        val formatTime: String? = null,
        val location: CachedLocation? = null,
        val status: String? = null,
        val registrationType: String? = null,
    ) : CachedQuizPayload()

    @Serializable
    @SerialName("Smuzi")
    data class Smuzi(
        override val id: String? = null,
        val title: String? = null,
        val description: String? = null,
        val image: String? = null,
        val price: Int? = null,
        val eventTime: CachedGameDate? = null,
        val location: CachedLocation? = null,
        val gameTypeLabel: String? = null,
        val url: String? = null,
    ) : CachedQuizPayload()
}

@Serializable
data class CachedGameDate(
    val dateTime: String,
    val day: String,
    val month: String,
    val time: String,
)

@Serializable
data class CachedLocation(
    val name: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,
    val city: String? = null,
)