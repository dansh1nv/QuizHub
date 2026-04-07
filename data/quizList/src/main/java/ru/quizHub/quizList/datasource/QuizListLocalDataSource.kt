package ru.quizHub.quizList.datasource

import kotlinx.coroutines.flow.Flow
import ru.quizHub.database.QuizDatabase
import ru.quizHub.database.models.QuizDBO

/**
 * Локальный источник данных для кэширования списка всех квизов.
 * Используется в QuizListRepository.
 */
class QuizListLocalDataSource(
    private val database: QuizDatabase,
) {

    fun getQuizzes(): Flow<List<QuizDBO>> {
        return database.quizDao.getAll()
    }

    suspend fun saveQuizzes(quizzes: List<QuizDBO>) {
        database.quizDao.clean()
        if (quizzes.isNotEmpty()) {
            database.quizDao.insert(quizzes)
        }
    }

    suspend fun clear() {
        database.quizDao.clean()
    }
}
