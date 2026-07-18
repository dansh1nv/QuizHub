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

    fun getQuizzesByCity(city: String): Flow<List<QuizDBO>> {
        return database.quizDao.getByCity(city)
    }

    suspend fun saveQuizzes(city: String, quizzes: List<QuizDBO>) {
        database.quizDao.deleteByCity(city)
        if (quizzes.isNotEmpty()) {
            database.quizDao.insert(quizzes)
        }
    }

    suspend fun replaceByCityAndOrganization(
        city: String,
        organization: String,
        quizzes: List<QuizDBO>,
    ) {
        database.quizDao.deleteByCityAndOrganization(city, organization)
        if (quizzes.isNotEmpty()) {
            database.quizDao.insert(quizzes)
        }
    }

    suspend fun clear() {
        database.quizDao.clean()
    }
}
