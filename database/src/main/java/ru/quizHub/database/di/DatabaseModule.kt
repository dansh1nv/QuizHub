package ru.quizHub.database.di

import android.app.Application
import org.koin.dsl.module
import ru.quizHub.database.QuizDatabase
import ru.quizHub.database.buildRoomQuizDatabase
import ru.quizHub.database.dao.QuizDao

fun databaseModule() = module {
    single<QuizDatabase> {
        val application = get<Application>()
        QuizDatabase(buildRoomQuizDatabase(application.applicationContext))
    }

    single<QuizDao> {
        val database = get<QuizDatabase>()
        database.quizDao
    }
}
