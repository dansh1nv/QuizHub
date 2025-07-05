package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.squiz.SquizRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.SquizDataMapper
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.repository.ISQuizRepository

class SquizRepository(
//    private val localDataSource: LocalDataSource,
    private val remoteDataSource: SquizRemoteDataSource,
    private val quizMapper: SquizDataMapper,
) : ISQuizRepository {

    override suspend fun getAllQuizzes(city: City): Flow<List<SQuiz>> {
        return remoteDataSource.getAllQuizzes(city.squizId)
            .map(quizMapper::map)
    }

}