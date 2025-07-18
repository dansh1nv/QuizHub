package ru.dansh1nv.quizapi.model.squiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Edition (
    @SerialName("uid")
    val uid: Long? = null,
    @SerialName("price")
    val price: String? = null,
    @SerialName("priceold")
    val priceOld: String? = null,
    @SerialName("sku")
    val sku: String? = null,
    @SerialName("quantity")
    val quantity: String? = null,
    @SerialName("img")
    val image: String? = null,
    @SerialName("descr")
    val packageNumber: String? = null,
)