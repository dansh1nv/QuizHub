package ru.quizHub.quizApi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleaseDTO(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("name")
    val packageNumber: String? = null,
    @SerialName("quote")
    val description: String? = null,
    @SerialName("special_tablet_banner")
    val image: String? = null,
    @SerialName("game_type")
    val gameFormat: Int? = null,
    @SerialName("datetime")
    val datetime: String? = null,
    @SerialName("format_date")
    val formatDate: String? = null,
    @SerialName("format_time")
    val formatTime: String? = null,
    @SerialName("price")
    val price: Int? = null,
    @SerialName("format_price")
    val formatPrice: String? = null,
    @SerialName("place")
    val location: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("latitude")
    val latitude: String? = null,
    @SerialName("longitude")
    val longitude: String? = null,
    @SerialName("game_difficulty")
    val difficulty: String? = null,
    @SerialName("status")
    val status: StatusDTO? = null,
    @SerialName("payment_method")
    val paymentMethod: Int? = null,
)