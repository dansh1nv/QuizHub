package ru.quizHub.settings.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.quizHub.settings.mappers.ThemeModeMapperUI
import ru.quizHub.settings.presentation.SettingsViewModel
import ru.quizHub.settings.presentation.ThemeManager

fun settingsModule() = module {
    factoryOf(::ThemeModeMapperUI)
    factoryOf(::SettingsViewModel)
    factoryOf(::ThemeManager)
}