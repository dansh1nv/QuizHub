package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.ShakerQuiz
import ru.quizHub.quizlist.models.common.City

interface IShakerQuizRepository {
    fun fetchQuizzes(city: City): Flow<List<ShakerQuiz>>
}