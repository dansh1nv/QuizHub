package ru.dansh1nv.quiz_list_domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.repository.ISQuizRepository

class SquizInteractor(
    private val repository: ISQuizRepository,
) {
    suspend fun getQuizList(): Flow<List<SQuiz>> {
        return repository.getAllQuizzes()
    }
}