package ru.quizHub.settings.presentation

import ru.quizHub.core.presentation.SideEffect

internal sealed class SettingsSideEffect : SideEffect {
    data object NavigateToTheme : SettingsSideEffect()
}