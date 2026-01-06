package ru.quizHub.settings.interactors

import ru.quizHub.settings.models.ThemeMode
import ru.quizHub.settings.repository.IThemePreferencesRepository

class ThemePreferencesInteractor(
    private val repository: IThemePreferencesRepository
) {
    suspend fun saveThemeMode(mode: ThemeMode) = repository.saveThemeMode(mode)

    fun getThemeMode() = repository.getThemeMode()
}