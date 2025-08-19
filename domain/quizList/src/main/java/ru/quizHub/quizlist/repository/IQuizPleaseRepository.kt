package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.common.City

interface IQuizPleaseRepository {

    suspend fun getQuizList(
        city: City,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPlease>>

}