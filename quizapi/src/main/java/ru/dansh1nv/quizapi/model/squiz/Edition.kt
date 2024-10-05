package ru.dansh1nv.quizapi.model.squiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Edition (
    @SerialName("uid")
    val uid: Long?,
    @SerialName("price")
    val price: String?,
    @SerialName("priceold")
    val priceOld: String?,
    @SerialName("sku")
    val sku: String?,
    @SerialName("quantity")
    val quantity: String?,
    @SerialName("img")
    val image: String?,
    @SerialName("descr")
    val packageNumber: String? = null,
)