package ru.quizHub.settings.presentation

import androidx.compose.runtime.Immutable
import ru.quizHub.core.presentation.ScreenState
import ru.quizHub.core.presentation.viewModel.BaseMviViewModel
import ru.quizHub.settings.models.ThemeModeUI

internal class SettingsViewModel : BaseMviViewModel<SettingsState, SettingsSideEffect, ScreenEvent>(
    SettingsState()
) {
    override fun handleEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnThemeClick -> navigateToTheme()
        }
    }

    private fun navigateToTheme() {
        postSideEffect(SettingsSideEffect.NavigateToTheme)
    }
}

@Immutable
internal data class SettingsState(
    val title: String = "Настройки",
    val currentTheme: ThemeModeUI = ThemeModeUI.System
) : ScreenState