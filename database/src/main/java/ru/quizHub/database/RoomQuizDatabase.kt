package ru.quizHub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.quizHub.common.Constants.DATABASE_NAME
import ru.quizHub.database.dao.QuizDao
import ru.quizHub.database.migrations.QuizDatabaseMigrations
import ru.quizHub.database.models.QuizDBO

class QuizDatabase internal constructor(private val database: RoomQuizDatabase) {
    val quizDao: QuizDao
        get() = database.quizDao()
}

@Database(entities = [QuizDBO::class], version = 4)
abstract class RoomQuizDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}

fun QuizDatabase(applicationContext: Context): QuizDatabase {
    val quizRoomDatabase = buildRoomQuizDatabase(applicationContext)
    return QuizDatabase(quizRoomDatabase)
}

fun buildRoomQuizDatabase(applicationContext: Context): RoomQuizDatabase {
    return Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        RoomQuizDatabase::class.java,
        DATABASE_NAME
    )
        .addMigrations(*QuizDatabaseMigrations.ALL)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
}
