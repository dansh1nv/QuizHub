package ru.dansh1nv.quizhub

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import ru.dansh1nv.database.di.databaseModule
import ru.dansh1nv.navigation.SharedScreen
import ru.dansh1nv.quiz.data.di.quizDataModule
import ru.dansh1nv.quiz.details.presentation.QuizDetailsScreen
import ru.dansh1nv.quiz.list.di.quizListModule
import ru.dansh1nv.quiz.list.presentation.QuizListScreen
import ru.dansh1nv.quiz_list_domain.di.quizListDomainModule
import ru.dansh1nv.quizapi.di.apiModule
import ru.dansh1nv.quizhub.di.appModule

class QuizHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initNavigation()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@QuizHubApplication)
            modules(
                appModule(),
                databaseModule(),
                quizListModule(),
                quizListDomainModule(),
                quizDataModule(),
                apiModule(),
            )
        }
    }

    private fun initNavigation() {
        ScreenRegistry {
            register<SharedScreen.QuizList> {
                QuizListScreen()
            }
            register<SharedScreen.QuizDetails> {
                QuizDetailsScreen()
            }
        }
    }

}