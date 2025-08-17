package ru.quizHub.quizList.datasource.squiz

import kotlinx.coroutines.flow.Flow
import ru.quizHub.database.QuizDatabase
import ru.quizHub.database.models.QuizDBO

class LocalDataSource(
    private val database: QuizDatabase,
) {

    fun getQuizzes(): Flow<List<QuizDBO>> {
        return database.quizDao.getAll()
    }

}