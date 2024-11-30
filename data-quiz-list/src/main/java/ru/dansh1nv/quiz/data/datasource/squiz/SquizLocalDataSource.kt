package ru.dansh1nv.quiz.data.datasource.squiz

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.database.QuizDatabase
import ru.dansh1nv.database.models.QuizDBO

class SquizLocalDataSource(
    private val database: QuizDatabase,
) {

    fun getQuizzes(): Flow<List<QuizDBO>> {
        return database.quizDao.getAll()
    }

}