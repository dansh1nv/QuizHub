package ru.quizHub.settings.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.quizHub.settings.interactors.ThemePreferencesInteractor

fun settingsDomainModule() = module {
    factoryOf(::ThemePreferencesInteractor)
}