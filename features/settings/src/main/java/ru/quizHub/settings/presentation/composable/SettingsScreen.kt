package ru.quizHub.settings.presentation.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.quizHub.core.navigation.destinations.ThemeSettingsDestination
import ru.quizHub.core.presentation.viewModel.viewModel
import ru.quizHub.settings.presentation.ScreenEvent
import ru.quizHub.settings.presentation.SettingsSideEffect
import ru.quizHub.settings.presentation.SettingsViewModel

@Composable
fun SettingsScreen(navController: NavHostController) {
    val viewModel = viewModel<SettingsViewModel> {
        parametersOf()
    }
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingsSideEffect.NavigateToTheme -> {
                navController.navigate(ThemeSettingsDestination.route)
            }
        }
    }

    BaseScreen(
        onUIEvent = viewModel::handleEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseScreen(
    onUIEvent: (ScreenEvent) -> Unit,
) {
    SettingsContent(onUIEvent = onUIEvent)
}