package ru.quizHub.settings.mappers

import ru.quizHub.settings.models.ThemeMode

class ThemeModeMapper {
    private companion object {
        const val LIGHT = "LIGHT"
        const val DARK = "DARK"
        const val SYSTEM = "SYSTEM"
        const val HIGH_CONTRAST = "HIGH_CONTRAST"
    }

    fun map(mode: ThemeMode): String = when (mode) {
        is ThemeMode.Light -> LIGHT
        is ThemeMode.Dark -> DARK
        is ThemeMode.System -> SYSTEM
        is ThemeMode.HighContrast -> HIGH_CONTRAST
    }

    fun mapToThemeMode(text: String): ThemeMode = when (text.uppercase()) {
        LIGHT -> ThemeMode.Light
        DARK -> ThemeMode.Dark
        SYSTEM -> ThemeMode.System
        HIGH_CONTRAST -> ThemeMode.HighContrast
        else -> ThemeMode.System
    }
}