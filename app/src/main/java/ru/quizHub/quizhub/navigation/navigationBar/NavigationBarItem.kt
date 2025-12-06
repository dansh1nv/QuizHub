package ru.quizHub.quizhub.navigation.navigationBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.quizHub.core.navigation.destinations.ProfileDestination
import ru.quizHub.core.navigation.destinations.QuizListDestination
import ru.quizHub.core.navigation.destinations.SettingsDestination
import ru.quizHub.quizhub.R

sealed class NavigationBarItem(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    object QuizList : NavigationBarItem(
        route = QuizListDestination.route,
        title = R.string.nav_bar_quizzes,
        icon = R.drawable.ic_list_line
    )

    object Profile : NavigationBarItem(
        route = ProfileDestination.route,
        title = R.string.nav_bar_profile,
        icon = R.drawable.ic_profile
    )

    object Settings : NavigationBarItem(
        route = SettingsDestination.route,
        title = R.string.nav_bar_settings,
        icon = R.drawable.ic_settings
    )
}