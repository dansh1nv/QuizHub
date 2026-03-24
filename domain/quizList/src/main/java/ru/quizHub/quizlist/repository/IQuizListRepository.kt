package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City

interface IQuizListRepository {
    fun getAllQuizList(city: City): Flow<List<Quiz>>
}