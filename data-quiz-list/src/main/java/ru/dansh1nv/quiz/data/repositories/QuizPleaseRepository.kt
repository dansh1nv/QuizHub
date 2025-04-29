package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.QuizPleaseDataMapper
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository

class QuizPleaseRepository(
    private val remoteDataSource: QuizPleaseRemoteDataSource,
    private val mapper: QuizPleaseDataMapper,
) : IQuizPleaseRepository {
    override suspend fun getQuizList(
        cityId: Int,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPlease>> {
        return remoteDataSource.getQuizList(
            cityId = cityId,
            pageNumber = pageNumber,
            pageSize = pageSize,
        ).map(mapper::mapToQuiz)
    }

}