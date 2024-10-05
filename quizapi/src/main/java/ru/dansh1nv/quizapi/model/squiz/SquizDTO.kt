package ru.dansh1nv.quizapi.model.squiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SquizDTO(
    @SerialName("uid")
    val id: Long,
    @SerialName("title")
    val title: String? = null,
    @SerialName("text")
    val text: String? = null,
    @SerialName("descr")
    val packageNumber: String? = null,
    @SerialName("price")
    val price: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("gallery")
    val galleryImage: String? = null,
    @SerialName("characteristics")
    val characteristics: List<Characteristic>? = null,
    @SerialName("editions")
    val editions: List<Edition>? = null,
    @SerialName("partuids")
    val cityId: String? = null,
)