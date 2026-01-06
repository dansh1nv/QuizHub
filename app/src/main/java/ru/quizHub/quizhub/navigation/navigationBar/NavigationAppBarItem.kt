package ru.quizHub.quizhub.navigation.navigationBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.quizHub.core.navigation.destinations.ProfileDestination
import ru.quizHub.core.navigation.destinations.QuizListDestination
import ru.quizHub.core.navigation.destinations.SettingsDestination
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.designsystem.theme.utils.`typealias`.UIString

sealed class NavigationAppBarItem(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    object QuizList : NavigationAppBarItem(
        route = QuizListDestination.route,
        title = UIString.nav_bar_quizzes,
        icon = UIDrawable.ic_list_line
    )

    object Profile : NavigationAppBarItem(
        route = ProfileDestination.route,
        title = UIString.nav_bar_profile,
        icon = UIDrawable.ic_profile
    )

    object Settings : NavigationAppBarItem(
        route = SettingsDestination.route,
        title = UIString.nav_bar_settings,
        icon = UIDrawable.ic_settings
    )
}