package ru.quizHub.settings.models

sealed class ThemeModeUI {
    object Light : ThemeModeUI()
    object Dark : ThemeModeUI()
    object System : ThemeModeUI()
    object HighContrast : ThemeModeUI()
}