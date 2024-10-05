package ru.dansh1nv.quizapi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleaseDTO(
    @SerialName("id")
    val id: Long?,
    @SerialName("title")
    val title: String?,
    @SerialName("name")
    val packageNumber: String?,
    @SerialName("quote")
    val description: String?,
    @SerialName("special_tablet_banner")
    val image: String?,
    @SerialName("game_type")
    val gameFormat: Int?,
    @SerialName("datetime")
    val datetime: String?,
    @SerialName("format_date")
    val formatDate: String?,
    @SerialName("format_time")
    val formatTime: String?,
    @SerialName("price")
    val price: Int?,
    @SerialName("format_price")
    val formatPrice: String?,
    @SerialName("place")
    val location: String?,
    @SerialName("address")
    val address: String?,
    @SerialName("city")
    val city: String?,
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("longitude")
    val longitude: String?,
    @SerialName("game_difficulty")
    val difficulty: String?,
    @SerialName("status")
    val status: StatusDTO?,
    @SerialName("payment_method")
    val paymentMethod: Int?,
)