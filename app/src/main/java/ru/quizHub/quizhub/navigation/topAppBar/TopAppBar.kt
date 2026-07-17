package ru.quizHub.quizhub.navigation.topAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.quizHub.designsystem.theme.elements.QuizHubTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior?,
    currentRoute: String?
) {
    val items = listOf(
        TopBarItem.ThemeSettings,
        TopBarItem.DevTools,
    )
    val currentItem = items.find { it.route == currentRoute }

    if (currentItem != null) {
        QuizHubTopAppBar(
            titleResId = currentItem.title,
            iconResId = currentItem.icon,
            onNavigationClick = { navController.navigateUp() },
            scrollBehavior = scrollBehavior
        )
    }
}