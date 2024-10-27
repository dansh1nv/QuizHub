package ru.dansh1nv.quizapi.model.shakerquiz

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShakerQuizResponseDTO(
    val message: String? = null,
    val data: ShakerQuizResponseDataDTO? = null,
)

@Serializable
data class ShakerQuizResponseDataDTO(
    @SerialName("items")
    val items: List<ShakerQuizItemDTO>? = null,
    val total: Int? = null,
    val page: Int? = null,
    val size: Int? = null,
    val pages: Int? = null,
    @SerialName("next_page")
    val nextPage: Int? = null,
    @SerialName("previous_page")
    val previousPage: Int? = null,
)

@Serializable
data class ShakerQuizItemDTO(
    @SerialName("name")
    val title: String? = null,
    @SerialName("city_id")
    val cityId: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("short_description")
    val shortDescription: String? = null,
    val weekday: String? = null,
    val number: String? = null,
    val status: String? = null,
    @SerialName("event_time")
    val eventTime: String? = null,
    val price: Double? = null,
    val currency: String? = null,
    val timezone: Int? = null,
    @SerialName("min_members_count")
    val minMembersCount: Int? = null,
    @SerialName("max_members_count")
    val maxMembersCount: Int? = null,
    @SerialName("image_id")
    val imageId: String? = null,
    @SerialName("location_id")
    val locationId: String? = null,
    @SerialName("theme_id")
    val themeId: String? = null,
    @SerialName("game_pack_id")
    val gamePackId: String? = null,
    val id: String? = null,
    val location: LocationDTO? = null,
    val image: ImageDTO? = null,
    @SerialName("owner_id")
    val ownerId: String? = null,
    @SerialName("capacity_status")
    val capacityStatus: String? = null,
)

@Serializable
data class LocationDTO(
    val name: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerialName("street")
    val street: String? = null,
    @SerialName("house_number")
    val houseNumber: String? = null,
    @SerialName("people_capacity")
    val peopleCapacity: Int? = null,
    @SerialName("team_capacity")
    val teamCapacity: Int? = null,
    @SerialName("is_adult")
    val isAdult: Boolean? = null,
    @SerialName("game_time")
    val gameTime: String? = null,
    val floor: String? = null,
    val metro: String? = null,
    @SerialName("location_info")
    val locationInfo: String? = null,
    @SerialName("city_id")
    val cityId: String? = null,
    val id: String? = null,
    val city: CityDTO? = null,
)

@Serializable
data class CityDTO(
    val name: String? = null,
    val alias: String? = null,
    val region: String? = null,
    val country: String? = null,
    @SerialName("is_default")
    val isDefault: Boolean? = null,
    @SerialName("vk_group_id")
    val vkGroupId: String? = null,
    val id: String? = null,
)

@Serializable
data class ImageDTO(
    @SerialName("file_format")
    val fileFormat: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val id: String? = null,
    val media: MediaDTO? = null,
)

@Serializable
data class MediaDTO(
    @SerialName("cached_link")
    val cachedLink: String? = null,
    val id: String? = null,
)
