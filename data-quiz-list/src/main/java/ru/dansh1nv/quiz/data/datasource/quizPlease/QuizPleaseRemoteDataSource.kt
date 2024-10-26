package ru.dansh1nv.quiz.data.datasource.quizPlease

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quizapi.api.QuizPleaseApi
import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseDTO

class QuizPleaseRemoteDataSource(
    private val api: QuizPleaseApi,
) {
    suspend fun getQuizList(
        cityId: Int,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPleaseDTO>> = api.getQuizzes(
        cityId = cityId,
        pageNumber = pageNumber,
        pageSize = pageSize,
    )
}