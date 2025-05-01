package ru.dansh1nv.quizhub.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.core.resourceManager.ResourceManager
import ru.dansh1nv.quizhub.MainActivityViewModel

fun appModule() = module {

    factory<IResourceManager> {
        ResourceManager(context = get())
    }

    viewModelOf(::MainActivityViewModel)
}