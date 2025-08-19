package ru.quizHub.quizlist.interactors

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.SQuiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.ISQuizRepository

class SquizInteractor(
    private val repository: ISQuizRepository,
) {
    suspend fun getQuizList(city: City): Flow<List<SQuiz>> {
        return repository.getAllQuizzes(city)
    }
}