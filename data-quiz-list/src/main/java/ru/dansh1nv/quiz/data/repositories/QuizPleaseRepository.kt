package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.QuizPleaseDataMapper
import ru.dansh1nv.quiz.data.models.toDTO
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository

class QuizPleaseRepository(
    private val remoteDataSource: QuizPleaseRemoteDataSource,
    private val mapper: QuizPleaseDataMapper,
) : IQuizPleaseRepository {
    override suspend fun getQuizList(
        cityId: City,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPlease>> {
        return remoteDataSource.getQuizList(
            cityId = cityId.toDTO().quizPleaseId,
            pageNumber = pageNumber,
            pageSize = pageSize,
        ).map(mapper::mapToQuiz)
    }

}