package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.quizHub.quizList.datasource.wowQuiz.WowQuizRemoteDataSource
import ru.quizHub.quizList.mappers.WowQuizDataMapper
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IWowQuizRepository

class WowQuizRepository(
    private val remoteDataSource: WowQuizRemoteDataSource,
    private val mapper: WowQuizDataMapper,
) : IWowQuizRepository {

    override fun getQuizList(
        city: City,
        page: Int,
        upcoming: Int,
    ): Flow<List<WowQuiz>> {
        return remoteDataSource.getQuizList(
            domain = city.wowQuizDomain,
            page = page,
            upcoming = upcoming,
        ).map(mapper::mapToWowQuiz)
    }
}
