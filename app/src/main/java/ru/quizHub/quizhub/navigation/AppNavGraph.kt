package ru.quizHub.quizhub.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.quizHub.core.navigation.destinations.QuizDetailsDestination
import ru.quizHub.core.navigation.destinations.QuizListDestination
import ru.quizHub.quiz.details.presentation.presentation.composable.QuizDetailsScreen
import ru.quizHub.quizList.presentation.composable.QuizListScreen

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