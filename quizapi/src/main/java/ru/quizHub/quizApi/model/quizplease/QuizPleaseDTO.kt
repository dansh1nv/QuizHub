package ru.quizHub.quizApi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleaseDTO(
    @SerialName("id")
    val id: String? = null,
    @SerialName("place")
    val place: QuizPleasePlaceDTO? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("block_with_text")
    val blockWithText: String? = null,
    @SerialName("quote")
    val quote: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("date_open_registration")
    val dateOpenRegistration: String? = null,
    @SerialName("people_have_places_to_reserve")
    val peopleHavePlacesToReserve: Int? = null,
    @SerialName("commands_have_places_to_reserve")
    val commandsHavePlacesToReserve: Int? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("level")
    val level: String? = null,
    @SerialName("show_account")
    val showAccount: Boolean? = null,
    @SerialName("price_type")
    val priceType: Int? = null,
    @SerialName("pay_method")
    val payMethod: Int? = null,
    @SerialName("free_enter")
    val freeEnter: Int? = null,
    @SerialName("free_entry_age_from")
    val freeEntryAgeFrom: Int? = null,
    @SerialName("game_number")
    val gameNumber: String? = null,
    @SerialName("game_type")
    val gameType: Int? = null,
    @SerialName("lottery_id")
    val lotteryId: Int? = null,
    @SerialName("price")
    val price: Int? = null,
    @SerialName("current_price")
    val currentPrice: String? = null,
    @SerialName("few_places_left")
    val fewPlacesLeft: Boolean? = null,
    @SerialName("lang")
    val lang: QuizPleaseLangDTO? = null,
    @SerialName("schedule_button_text")
    val scheduleButtonText: String? = null,
    @SerialName("schedule_button_link")
    val scheduleButtonLink: String? = null,
    @SerialName("link_registration_iframe")
    val linkRegistrationIframe: String? = null,
    @SerialName("game_button_text")
    val gameButtonText: String? = null,
    @SerialName("game_button_link")
    val gameButtonLink: String? = null,
    @SerialName("welcome_inscription")
    val welcomeInscription: String? = null,
    @SerialName("package_number")
    val packageNumber: String? = null,
    @SerialName("is_show_promo_field")
    val isShowPromoField: Boolean? = null,
    @SerialName("link_contact_button")
    val linkContactButton: String? = null,
    @SerialName("link_to_donations")
    val linkToDonations: String? = null,
    @SerialName("link_facecast")
    val linkFacecast: String? = null,
    @SerialName("max_participants")
    val maxParticipants: Int? = null,
    @SerialName("link_results_page")
    val linkResultsPage: String? = null,
    @SerialName("banner_for_streams")
    val bannerForStreams: String? = null,
    @SerialName("banner_for_streams_desktop")
    val bannerForStreamsDesktop: String? = null,
    @SerialName("banner_for_streams_tablet")
    val bannerForStreamsTablet: String? = null,
    @SerialName("banner_for_streams_mobile")
    val bannerForStreamsMobile: String? = null,
    @SerialName("people_count_until_reserve")
    val peopleCountUntilReserve: Int? = null,
    @SerialName("people_count_until_no_places")
    val peopleCountUntilNoPlaces: Int? = null,
    @SerialName("hide_registration_form_page")
    val hideRegistrationFormPage: Boolean? = null,
    @SerialName("template")
    val template: QuizPleaseTemplateDTO? = null,
)

@Serializable
data class QuizPleaseLangDTO(
    @SerialName("code")
    val code: String? = null,
    @SerialName("title")
    val title: String? = null,
)
