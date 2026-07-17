package ru.quizHub.core.featureToggle

import kotlinx.serialization.Serializable

@Serializable
data class FeatureToggleState(
    val bottomNavigationEnabled: Boolean = false,
    val isFavouriteFeatureEnabled: Boolean = false,
    val isFiltersFeatureEnabled: Boolean = true,
    val isSortFeatureEnabled: Boolean = true,
    val isCalendarFeatureEnable: Boolean = true,
    val cardDetails: Boolean = false,
)

enum class FeatureToggleKey {
    BOTTOM_NAVIGATION,
    FAVOURITE,
    FILTERS,
    SORT,
    CALENDAR,
    CARD_DETAILS,
}

data class FeatureToggleItem(
    val key: FeatureToggleKey,
    val title: String,
    val description: String,
)

object FeatureToggleCatalog {
    val items: List<FeatureToggleItem> = listOf(
        FeatureToggleItem(
            key = FeatureToggleKey.BOTTOM_NAVIGATION,
            title = "Bottom navigation",
            description = "Показывать нижнее меню навигации",
        ),
        FeatureToggleItem(
            key = FeatureToggleKey.FAVOURITE,
            title = "Favourites tab",
            description = "Вкладка «Избранное» в списке квизов",
        ),
        FeatureToggleItem(
            key = FeatureToggleKey.FILTERS,
            title = "Filters",
            description = "Кнопка фильтров в хедере",
        ),
        FeatureToggleItem(
            key = FeatureToggleKey.SORT,
            title = "Sorting",
            description = "Индикатор и выбор сортировки",
        ),
        FeatureToggleItem(
            key = FeatureToggleKey.CALENDAR,
            title = "Calendar",
            description = "Кнопка календаря в хедере",
        ),
        FeatureToggleItem(
            key = FeatureToggleKey.CARD_DETAILS,
            title = "Card details",
            description = "Переход на детализацию по клику на карточку",
        ),
    )
}

fun FeatureToggleState.get(key: FeatureToggleKey): Boolean = when (key) {
    FeatureToggleKey.BOTTOM_NAVIGATION -> bottomNavigationEnabled
    FeatureToggleKey.FAVOURITE -> isFavouriteFeatureEnabled
    FeatureToggleKey.FILTERS -> isFiltersFeatureEnabled
    FeatureToggleKey.SORT -> isSortFeatureEnabled
    FeatureToggleKey.CALENDAR -> isCalendarFeatureEnable
    FeatureToggleKey.CARD_DETAILS -> cardDetails
}

fun FeatureToggleState.with(key: FeatureToggleKey, enabled: Boolean): FeatureToggleState = when (key) {
    FeatureToggleKey.BOTTOM_NAVIGATION -> copy(bottomNavigationEnabled = enabled)
    FeatureToggleKey.FAVOURITE -> copy(isFavouriteFeatureEnabled = enabled)
    FeatureToggleKey.FILTERS -> copy(isFiltersFeatureEnabled = enabled)
    FeatureToggleKey.SORT -> copy(isSortFeatureEnabled = enabled)
    FeatureToggleKey.CALENDAR -> copy(isCalendarFeatureEnable = enabled)
    FeatureToggleKey.CARD_DETAILS -> copy(cardDetails = enabled)
}