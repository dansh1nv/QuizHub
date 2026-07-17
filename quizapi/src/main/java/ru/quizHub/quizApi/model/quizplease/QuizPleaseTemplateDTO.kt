package ru.quizHub.quizApi.model.quizplease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizPleaseTemplateDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("game_number")
    val gameNumber: String? = null,
    @SerialName("game_level")
    val gameLevel: String? = null,
    @SerialName("welcome_text")
    val welcomeText: String? = null,
    @SerialName("quote")
    val quote: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("block")
    val block: String? = null,
    @SerialName("hidden")
    val hidden: Boolean? = null,
    @SerialName("game_title")
    val gameTitle: String? = null,
    @SerialName("free")
    val free: Boolean? = null,
    @SerialName("game_type")
    val gameType: Int? = null,
    @SerialName("is_hide_game_number")
    val isHideGameNumber: Boolean? = null,
    @SerialName("is_exclude_from_rating")
    val isExcludeFromRating: Boolean? = null,
    @SerialName("is_bingo_register_link")
    val isBingoRegisterLink: Boolean? = null,
    @SerialName("is_show_promo_field")
    val isShowPromoField: Boolean? = null,
    @SerialName("hide_for_mobile")
    val hideForMobile: Boolean? = null,
    @SerialName("game_underlay")
    val gameUnderlay: String? = null,
    @SerialName("background_pc")
    val backgroundPc: String? = null,
    @SerialName("background_tablet")
    val backgroundTablet: String? = null,
    @SerialName("background_phone")
    val backgroundPhone: String? = null,
    @SerialName("category")
    val category: QuizPleaseCategoryDTO? = null,
)

@Serializable
data class QuizPleaseCategoryDTO(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("is_visible_in_filters")
    val isVisibleInFilters: Boolean? = null,
    @SerialName("category")
    val category: String? = null,
    @SerialName("order")
    val order: Int? = null,
    @SerialName("slug")
    val slug: String? = null,
)