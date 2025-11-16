package ru.quizHub.quizApi.model.rudagames

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RudaGamesDTO(
    @SerialName("uuid")
    val id: String? = null,
    @SerialName("game_name")
    val title: String? = null,
    @SerialName("displayed_game_name")
    val displayedName: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("media_banner")
    val image: MediaBanner? = null,
    @SerialName("played_at")
    val datetime: String? = null,
    @SerialName("time")
    val time: String? = null,
    @SerialName("price")
    val price: Int? = null,
    @SerialName("currency")
    val currency: String?,
    @SerialName("payment_type")
    val paymentType: String? = null,
    @SerialName("place")
    val location: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("city_link_name")
    val city: String? = null,
    @SerialName("game_type")
    val gameType: String? = null,
    @SerialName("link_status")
    val status: String? = null,
    @SerialName("is_registration_opened")
    val isRegistrationOpened: Boolean? = null,
    @SerialName("min_team_players")
    val minMembersCount: Int? = null,
    @SerialName("max_team_players")
    val maxMembersCount: Int? = null
)

@Serializable
data class MediaBanner(
    @SerialName("head")
    val head: String? = null
)