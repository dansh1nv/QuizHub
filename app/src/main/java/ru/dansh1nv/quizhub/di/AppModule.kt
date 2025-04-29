package ru.dansh1nv.quizhub.di

import org.koin.dsl.module
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.core.resourceManager.ResourceManager

fun appModule() = module {
    factory<IResourceManager> {
        ResourceManager(context = get())
    }
}