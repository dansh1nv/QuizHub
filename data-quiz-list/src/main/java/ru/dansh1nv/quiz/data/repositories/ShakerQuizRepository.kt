package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.ShakerQuizDataMapper
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.quiz_list_domain.repository.IShakerQuizRepository

class ShakerQuizRepository(
    private val remoteDataSource: ShakerQuizRemoteDataSource,
//    private val localDataSource: ShakerQuizLocalDataSource,
    private val mapper: ShakerQuizDataMapper,
): IShakerQuizRepository {
    override suspend fun fetchQuizzes(): Flow<List<ShakerQuiz>> {
        return remoteDataSource.getQuizList()
            .map(mapper::mapToShakerQuiz)
    }
}

