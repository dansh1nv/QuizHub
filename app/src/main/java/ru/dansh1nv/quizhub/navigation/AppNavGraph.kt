package ru.dansh1nv.quizhub.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.dansh1nv.core.navigation.destinations.QuizDetailsDestination
import ru.dansh1nv.core.navigation.destinations.QuizListDestination
import ru.dansh1nv.quiz.details.presentation.presentation.composable.QuizDetailsScreen
import ru.dansh1nv.quiz.list.presentation.composable.QuizListScreen

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