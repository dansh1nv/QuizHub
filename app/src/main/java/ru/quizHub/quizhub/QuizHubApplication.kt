package ru.quizHub.quizhub

import android.app.Application
import com.squareup.leakcanary.core.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import ru.quizHub.core.di.coreModule
import ru.quizHub.database.di.databaseModule
import ru.quizHub.quizList.di.quizListModule
import ru.quizHub.quizApi.di.apiModule
import ru.quizHub.quizhub.di.appModule
import ru.quizHub.quizList.di.quizDataModule
import ru.quizHub.quizlist.di.quizListDomainModule
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
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@QuizHubApplication)
            modules(
                appModule(),
                coreModule(),
                databaseModule(),
                quizListModule(),
                quizListDomainModule(),
                quizDataModule(),
                apiModule(),
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}