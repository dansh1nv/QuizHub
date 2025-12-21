package ru.quizHub.quizhub.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.quizHub.core.navigation.destinations.ProfileDestination
import ru.quizHub.core.navigation.destinations.QuizDetailsDestination
import ru.quizHub.core.navigation.destinations.QuizListDestination
import ru.quizHub.core.navigation.destinations.SettingsDestination
import ru.quizHub.core.navigation.destinations.ThemeSettingsDestination
import ru.quizHub.profile.presentation.composable.ProfileScreen
import ru.quizHub.quiz.details.presentation.presentation.composable.QuizDetailsScreen
import ru.quizHub.quizList.presentation.composable.QuizListScreen
import ru.quizHub.settings.presentation.composable.SettingsScreen
import ru.quizHub.settings.presentation.composable.ThemeSettingsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    onCloseApp: () -> Unit
) {
    BackHandler {
        if (!navController.popBackStack()) {
            onCloseApp.invoke()
        }
    }
    NavHost(navController = navController, startDestination = QuizListDestination.route) {
        quizList(navController)
        quizDetails(navController)
        settings(navController)
        profile(navController)
    }
}

fun NavGraphBuilder.quizList(navController: NavHostController) {
    composable(QuizListDestination.route) {
        QuizListScreen(navController)
    }
}

fun NavGraphBuilder.quizDetails(navController: NavHostController) {
    composable(QuizDetailsDestination.route) {
        QuizDetailsScreen()
    }
}

fun NavGraphBuilder.profile(bavController: NavHostController) {
    composable(ProfileDestination.route) {
        ProfileScreen()
    }
}

fun NavGraphBuilder.settings(navController: NavHostController) {
    composable(SettingsDestination.route) {
        SettingsScreen(navController)
    }
    composable(ThemeSettingsDestination.route) {
        ThemeSettingsScreen()
    }
}