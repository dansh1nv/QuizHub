package ru.quizHub.settings.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.quizHub.core.datastore.AppDataStore
import ru.quizHub.settings.mappers.ThemeModeMapper
import ru.quizHub.settings.models.ThemeMode
import ru.quizHub.settings.repository.IThemePreferencesRepository

class ThemePreferencesRepository(
    private val appDataStore: AppDataStore,
    private val mapper: ThemeModeMapper
) : IThemePreferencesRepository {
    override suspend fun saveThemeMode(mode: ThemeMode) {
        appDataStore.update {
            copy(themeMode = mapper.map(mode))
        }
    }

    override fun getThemeMode(): Flow<ThemeMode> {
        return appDataStore.dataFlow.map { preferences ->
            mapper.mapToThemeMode(preferences.themeMode)
        }
    }
}