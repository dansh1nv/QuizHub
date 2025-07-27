package ru.dansh1nv.quizhub.di

import androidx.compose.material3.SnackbarHostState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.core.resourceManager.ResourceManager
import ru.dansh1nv.quizhub.MainActivityViewModel

fun appModule() = module {

    factory<IResourceManager> {
        ResourceManager(context = get())
    }

    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single<SnackbarHostState> { SnackbarHostState() }

    viewModelOf(::MainActivityViewModel)
}