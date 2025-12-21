package ru.quizHub.settings.mappers

import ru.quizHub.settings.models.ThemeModeUI
import ru.quizHub.settings.models.ThemeMode

class ThemeModeMapperUI {
    fun mapToThemeModeUI(mode: ThemeMode): ThemeModeUI = when (mode) {
        is ThemeMode.Light -> ThemeModeUI.Light
        is ThemeMode.Dark -> ThemeModeUI.Dark
        is ThemeMode.System -> ThemeModeUI.System
        is ThemeMode.HighContrast -> ThemeModeUI.HighContrast
    }

    fun mapToThemeMode(mode: ThemeModeUI): ThemeMode = when (mode) {
        is ThemeModeUI.Light -> ThemeMode.Light
        is ThemeModeUI.Dark -> ThemeMode.Dark
        is ThemeModeUI.System -> ThemeMode.System
        is ThemeModeUI.HighContrast -> ThemeMode.HighContrast
    }
}