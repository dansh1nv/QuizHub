package ru.quizHub.quizlist.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City

class QuizListInteractor(
    private val repository: ru.quizHub.quizlist.repository.IQuizListRepository,
) {
    val quizListFlow: StateFlow<List<Quiz>> = repository.quizListFlow

    fun getAllQuizList(city: City): Flow<List<Quiz>> = repository.getAllQuizList(city)

    fun fetchQuizList(city: City) {
        repository.fetchAllQuizList(city)
    }
}