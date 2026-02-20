package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City

interface IQuizListRepository {
    val quizListFlow: StateFlow<List<Quiz>>
    fun getAllQuizList(city: City): Flow<List<Quiz>>
}