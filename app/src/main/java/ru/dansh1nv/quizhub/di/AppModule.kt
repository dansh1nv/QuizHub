package ru.dansh1nv.quizhub.di

import org.koin.dsl.module
import ru.dansh1nv.common.resourceManager.IResourceManager
import ru.dansh1nv.common.resourceManager.ResourceManager

fun appModule() = module {
    factory<IResourceManager> { ResourceManager(get()) }
}