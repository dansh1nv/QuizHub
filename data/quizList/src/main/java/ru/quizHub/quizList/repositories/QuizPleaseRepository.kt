package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.quizHub.quizList.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.quizHub.quizList.mappers.QuizPleaseDataMapper
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IQuizPleaseRepository

class QuizPleaseRepository(
    private val remoteDataSource: QuizPleaseRemoteDataSource,
    private val mapper: QuizPleaseDataMapper,
) : IQuizPleaseRepository {
    override suspend fun getQuizList(
        city: City,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPlease>> {
        return remoteDataSource.getQuizList(
            cityId = city.quizPleaseId,
            pageNumber = pageNumber,
            pageSize = pageSize,
        ).map(mapper::mapToQuiz)
    }

}