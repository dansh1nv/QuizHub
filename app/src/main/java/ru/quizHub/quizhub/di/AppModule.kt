package ru.quizHub.quizhub.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.quizHub.core.resourceManager.IResourceManager
import ru.quizHub.core.resourceManager.ResourceManager
import ru.quizHub.quizhub.MainActivityViewModel

fun appModule() = module {

    factory<IResourceManager> {
        ResourceManager(context = get())
    }

    viewModelOf(::MainActivityViewModel)
}