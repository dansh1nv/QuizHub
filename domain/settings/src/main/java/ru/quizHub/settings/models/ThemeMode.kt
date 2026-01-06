package ru.quizHub.settings.models

sealed class ThemeMode {
    object Light : ThemeMode()
    object Dark : ThemeMode()
    object System : ThemeMode()
    object HighContrast : ThemeMode()
}