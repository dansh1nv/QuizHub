package ru.quizHub.quizhub

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import ru.quizHub.core.di.coreModule
import ru.quizHub.database.di.databaseModule
import ru.quizHub.quizApi.di.apiModule
import ru.quizHub.quizList.di.quizDataModule
import ru.quizHub.quizList.di.quizListModule
import ru.quizHub.quizhub.di.appModule
import ru.quizHub.quizhub.utils.TimberLogger
import ru.quizHub.quizlist.di.quizListDomainModule
import ru.quizHub.settings.di.settingsDataModule
import ru.quizHub.settings.di.settingsDomainModule
import ru.quizHub.settings.di.settingsModule
import timber.log.Timber

class QuizHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    private fun initKoin() {
        startKoin {
            logger(TimberLogger())
            androidContext(this@QuizHubApplication)
            modules(
                appModule(),
                coreModule(),
                databaseModule(),
                quizListModule(),
                quizListDomainModule(),
                quizDataModule(),
                settingsModule(),
                settingsDomainModule(),
                settingsDataModule(),
                apiModule(),
            )
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}