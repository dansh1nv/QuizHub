package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.quizHub.quizList.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.quizHub.quizList.mappers.ShakerQuizDataMapper
import ru.quizHub.quizlist.models.ShakerQuiz
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IShakerQuizRepository

class ShakerQuizRepository(
    private val remoteDataSource: ShakerQuizRemoteDataSource,
    private val mapper: ShakerQuizDataMapper,
): IShakerQuizRepository {
    override fun fetchQuizzes(city: City): Flow<List<ShakerQuiz>> {
        return remoteDataSource.getQuizList(city.shakerQuizId)
            .map(mapper::mapToShakerQuiz)
    }
}

