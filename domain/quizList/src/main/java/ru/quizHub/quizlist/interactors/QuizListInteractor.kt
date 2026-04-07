package ru.quizHub.quizlist.interactors

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IQuizListRepository

class QuizListInteractor(
    private val repository: IQuizListRepository,
) {
    fun getAllQuizList(city: City, forceRefresh: Boolean = false): Flow<List<Quiz>> =
        repository.getAllQuizList(city, forceRefresh)
}
