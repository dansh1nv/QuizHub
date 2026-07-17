package ru.quizHub.quizApi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleasePlaceDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("city_id")
    val cityId: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("address_ru")
    val addressRu: String? = null,
    @SerialName("special")
    val special: String? = null,
    @SerialName("option")
    val option: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("covid_free")
    val covidFree: Boolean? = null,
    @SerialName("anti_covid_no")
    val antiCovidNo: Boolean? = null,
    @SerialName("bar_link")
    val barLink: String? = null,
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("menu")
    val menu: String? = null,
    @SerialName("people_have_places_to_reserve")
    val peopleHavePlacesToReserve: Int? = null,
    @SerialName("people_reserve_no_places")
    val peopleReserveNoPlaces: Int? = null,
    @SerialName("commands_have_places_to_reserve")
    val commandsHavePlacesToReserve: Int? = null,
    @SerialName("commands_reserve_no_places")
    val commandsReserveNoPlaces: Int? = null,
    @SerialName("city")
    val city: QuizPleaseCityDTO? = null,
    @SerialName("images")
    val images: List<QuizPleasePlaceImageDTO>? = null,
)

@Serializable
data class QuizPleasePlaceImageDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("place_id")
    val placeId: Int? = null,
    @SerialName("image")
    val image: String? = null,
)

@Serializable
data class QuizPleaseCityDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("title_en")
    val titleEn: String? = null,
    @SerialName("slug")
    val slug: String? = null,
    @SerialName("map")
    val map: String? = null,
    @SerialName("timezone_id")
    val timezoneId: Int? = null,
    @SerialName("reservation")
    val reservation: Boolean? = null,
    @SerialName("show_on_index")
    val showOnIndex: Boolean? = null,
    @SerialName("city_is_connected")
    val cityIsConnected: Boolean? = null,
    @SerialName("video_price_classic")
    val videoPriceClassic: Int? = null,
    @SerialName("video_price_movie_music")
    val videoPriceMovieMusic: Int? = null,
    @SerialName("video_price_online")
    val videoPriceOnline: Int? = null,
    @SerialName("video_price_english")
    val videoPriceEnglish: Int? = null,
    @SerialName("video_price_teens")
    val videoPriceTeens: Int? = null,
    @SerialName("video_price_corporate")
    val videoPriceCorporate: Int? = null,
    @SerialName("royalty_percent")
    val royaltyPercent: Int? = null,
    @SerialName("royalty_vacation_start")
    val royaltyVacationStart: String? = null,
    @SerialName("royalty_vacation_stop")
    val royaltyVacationStop: String? = null,
    @SerialName("country_id")
    val countryId: Int? = null,
    @SerialName("key")
    val key: String? = null,
    @SerialName("manager_id")
    val managerId: Int? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("country")
    val country: QuizPleaseCountryDTO? = null,
    @SerialName("timezone")
    val timezone: QuizPleaseTimezoneDTO? = null,
)

@Serializable
data class QuizPleaseCountryDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("default_city_id")
    val defaultCityId: Int? = null,
    @SerialName("currency")
    val currency: QuizPleaseCurrencyDTO? = null,
)

@Serializable
data class QuizPleaseCurrencyDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("symbol")
    val symbol: String? = null,
    @SerialName("rate")
    val rate: Double? = null,
    @SerialName("is_before")
    val isBefore: Boolean? = null,
)

@Serializable
data class QuizPleaseTimezoneDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("title_ru")
    val titleRu: String? = null,
    @SerialName("utc_offset")
    val utcOffset: String? = null,
    @SerialName("title_utc")
    val titleUtc: String? = null,
)