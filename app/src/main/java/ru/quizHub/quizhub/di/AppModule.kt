package ru.quizHub.quizhub.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.quizHub.core.resourceManager.IResourceManager
import ru.quizHub.core.resourceManager.ResourceManager
import ru.quizHub.quizhub.MainActivityViewModel
import ru.quizHub.quizhub.devtools.DevToolsViewModel

fun appModule() = module {

    factory<IResourceManager> {
        ResourceManager(context = get())
    }

    viewModelOf(::MainActivityViewModel)
    viewModelOf(::DevToolsViewModel)
}