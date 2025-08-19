package ru.quizHub.core.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.quizHub.core.datastore.AppDataStore
import ru.quizHub.core.datastore.AppPreferences
import ru.quizHub.core.presentation.ActionEventsListener
import ru.quizHub.core.presentation.IntentErrorMapper
import ru.quizHub.core.presentation.SnackbarListener
import ru.quizHub.designsystem.theme.bottomsheet.controller.BottomSheetController
import ru.quizHub.designsystem.theme.bottomsheet.controller.BottomSheetControllerImpl

fun coreModule() = module {
    factoryOf(::BottomSheetControllerImpl) bind BottomSheetController::class
    singleOf(::ActionEventsListener)
    singleOf(::SnackbarListener)
    singleOf(::IntentErrorMapper)
    single<AppDataStore> {
        val dataStore = DataStoreFactory.create(
            serializer = AppPreferences.Serializer(Json),
            produceFile = { androidContext().preferencesDataStoreFile("settings") },
            corruptionHandler = ReplaceFileCorruptionHandler { AppPreferences() }
        )
        return@single AppDataStore(dataStore)
    }
}