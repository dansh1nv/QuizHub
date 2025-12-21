package ru.quizHub.settings.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.settings.models.ThemeMode

interface IThemePreferencesRepository {
    suspend fun saveThemeMode(mode: ThemeMode)
    fun getThemeMode(): Flow<ThemeMode>
}