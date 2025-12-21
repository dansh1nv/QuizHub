package ru.quizHub.quizhub.navigation.navigationBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun NavigationAppBar(
    navController: NavController,
    currentRoute: String?
) {
    val items = listOf(
        NavigationBarItem.QuizList,
        NavigationBarItem.Profile,
        NavigationBarItem.Settings
    )

    val shouldShowNavigationBar = items.any { it.route == currentRoute }

    if (shouldShowNavigationBar) {
        NavigationBar(
            containerColor = QuizHubTheme.colorScheme.surfaceContainer,
            contentColor = QuizHubTheme.colorScheme.onSurface
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = stringResource(id = item.title),
                            tint = if (selected) {
                                QuizHubTheme.colorScheme.onSecondaryContainer
                            } else {
                                QuizHubTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            color = if (selected) {
                                QuizHubTheme.colorScheme.secondary
                            } else {
                                QuizHubTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = QuizHubTheme.colorScheme.onSecondaryContainer,
                        selectedTextColor = QuizHubTheme.colorScheme.secondary,
                        indicatorColor = QuizHubTheme.colorScheme.secondaryContainer,
                        unselectedIconColor = QuizHubTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = QuizHubTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}