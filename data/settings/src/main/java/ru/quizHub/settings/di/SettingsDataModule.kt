package ru.quizHub.settings.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.quizHub.settings.mappers.ThemeModeMapper
import ru.quizHub.settings.repositories.ThemePreferencesRepository
import ru.quizHub.settings.repository.IThemePreferencesRepository

fun settingsDataModule() = module {
    factoryOf(::ThemeModeMapper)
    singleOf(::ThemePreferencesRepository) bind IThemePreferencesRepository::class
}