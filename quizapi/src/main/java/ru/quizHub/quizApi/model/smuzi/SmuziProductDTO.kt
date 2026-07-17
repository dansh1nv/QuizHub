package ru.quizHub.quizApi.model.smuzi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmuziProductsResponse(
    @SerialName("products")
    val products: List<SmuziProductDTO>? = null,
)

@Serializable
data class SmuziProductDTO(
    @SerialName("uid")
    val id: Long,
    @SerialName("title")
    val title: String? = null,
    @SerialName("text")
    val text: String? = null,
    @SerialName("descr")
    val description: String? = null,
    @SerialName("price")
    val price: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("gallery")
    val gallery: String? = null,
    @SerialName("characteristics")
    val characteristics: List<SmuziCharacteristicDTO>? = null,
    @SerialName("json_options")
    val jsonOptions: String? = null,
    @SerialName("mark")
    val mark: String? = null,
    @SerialName("quantity")
    val quantity: String? = null,
)

@Serializable
data class SmuziCharacteristicDTO(
    @SerialName("title")
    val title: String? = null,
    @SerialName("value")
    val value: String? = null,
)

@Serializable
data class SmuziGalleryItemDTO(
    @SerialName("img")
    val img: String? = null,
)

@Serializable
data class SmuziJsonOptionDTO(
    @SerialName("title")
    val title: String? = null,
    @SerialName("values")
    val values: List<String>? = null,
)
