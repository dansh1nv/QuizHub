package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.SQuiz
import ru.quizHub.quizlist.models.common.City

interface ISQuizRepository {

    suspend fun getAllQuizzes(city: City): Flow<List<SQuiz>>

}