package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.quizHub.quizList.datasource.squiz.SquizRemoteDataSource
import ru.quizHub.quizList.mappers.SquizDataMapper
import ru.quizHub.quizlist.models.SQuiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.ISQuizRepository

class SquizRepository(
//    private val localDataSource: LocalDataSource,
    private val remoteDataSource: SquizRemoteDataSource,
    private val quizMapper: SquizDataMapper,
) : ISQuizRepository {

    override suspend fun getAllQuizzes(city: City): Flow<List<SQuiz>> {
        return remoteDataSource.getQuizList(city.squizId)
            .map(quizMapper::map)
    }

}