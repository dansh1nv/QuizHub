package ru.dansh1nv.quizhub

import android.app.Application
import ru.dansh1nv.quizapi.di.apiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import ru.dansh1nv.database.di.databaseModule
import ru.dansh1nv.quiz.data.di.quizDataModule
import ru.dansh1nv.quiz.list.di.quizListModule
import ru.dansh1nv.quiz_list_domain.di.quizListDomainModule

class QuizHubApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@QuizHubApplication)
            modules(
                databaseModule,
                quizListModule,
                quizListDomainModule,
                quizDataModule,
                apiModule,
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}