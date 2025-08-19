package ru.quizHub.quizList.datasource.quizPlease

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizApi.api.QuizPleaseApi
import ru.quizHub.quizApi.model.quizplease.QuizPleaseDTO

class QuizPleaseRemoteDataSource(
    private val api: QuizPleaseApi,
) {
    fun getQuizList(
        cityId: Long?,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPleaseDTO>> = api.getQuizzes(
        cityId = cityId,
        pageNumber = pageNumber,
        pageSize = pageSize,
    )
}