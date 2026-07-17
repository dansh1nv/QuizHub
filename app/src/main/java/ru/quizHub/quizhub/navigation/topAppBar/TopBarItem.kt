package ru.quizHub.quizhub.navigation.topAppBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.quizHub.core.navigation.destinations.DevToolsDestination
import ru.quizHub.core.navigation.destinations.ThemeSettingsDestination
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.designsystem.theme.utils.`typealias`.UIString
import ru.quizHub.quizhub.R

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

    object DevTools : TopBarItem(
        route = DevToolsDestination.route,
        title = R.string.devtools_title,
        icon = UIDrawable.ic_arrow_left_s
    )
}