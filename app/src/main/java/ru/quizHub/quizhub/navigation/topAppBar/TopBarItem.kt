package ru.quizHub.quizhub.navigation.topAppBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.quizHub.core.navigation.destinations.ThemeSettingsDestination
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.designsystem.theme.utils.`typealias`.UIString

sealed class TopBarItem(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    object ThemeSettings : TopBarItem(
        route = ThemeSettingsDestination.route,
        title = UIString.settings_theme_title,
        icon = UIDrawable.ic_arrow_left_s
    )
}