package ru.quizHub.quizApi.model.wowquiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WowGameDTO(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("theme")
    val theme: String? = null,
    @SerialName("template")
    val template: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("short_description")
    val shortDescription: String? = null,
    @SerialName("price")
    val price: Int? = null,
    @SerialName("currency")
    val currency: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("bar")
    val bar: WowBarDTO? = null,
    @SerialName("registration_type")
    val registrationType: String? = null,
)

@Serializable
data class WowBarDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("metro_station")
    val metroStation: String? = null,
    @SerialName("latitude")
    val latitude: Double? = null,
    @SerialName("longitude")
    val longitude: Double? = null,
)
