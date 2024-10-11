package ru.dansh1nv.database.di

import android.app.Application
import androidx.room.Room
import org.koin.dsl.module
import ru.dansh1nv.common.Constants.DATABASE_NAME
import ru.dansh1nv.database.QuizDatabase
import ru.dansh1nv.database.RoomQuizDatabase
import ru.dansh1nv.database.dao.QuizDao

fun databaseModule() = module {
    single<QuizDatabase> {
        val application = get<Application>()
        val quizRoomDatabase = Room.databaseBuilder(
            checkNotNull(application.applicationContext),
            RoomQuizDatabase::class.java,
            DATABASE_NAME
        ).build()
        QuizDatabase(quizRoomDatabase)
    }

    single<QuizDao> {
        val database = get<QuizDatabase>()
        database.quizDao
    }

}