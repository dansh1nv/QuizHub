package ru.dansh1nv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.dansh1nv.common.Constants.DATABASE_NAME
import ru.dansh1nv.database.dao.QuizDao
import ru.dansh1nv.database.models.QuizDBO

class QuizDatabase internal constructor(private val database: RoomQuizDatabase) {
    val quizDao: QuizDao
        get() = database.quizDao()
}

@Database(entities = [QuizDBO::class], version = 1)
abstract class RoomQuizDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}

fun QuizDatabase(applicationContext: Context): QuizDatabase {
    val quizRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        RoomQuizDatabase::class.java,
        DATABASE_NAME
    ).build()
    return QuizDatabase(quizRoomDatabase)
}