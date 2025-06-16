package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.shakerQuiz.ShakerQuizRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.ShakerQuizDataMapper
import ru.dansh1nv.quiz.data.models.toDTO
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.repository.IShakerQuizRepository

class ShakerQuizRepository(
    private val remoteDataSource: ShakerQuizRemoteDataSource,
//    private val localDataSource: ShakerQuizLocalDataSource,
    private val mapper: ShakerQuizDataMapper,
): IShakerQuizRepository {
    override fun fetchQuizzes(city: City): Flow<List<ShakerQuiz>> {
        return remoteDataSource.getQuizList(city.toDTO().shakerQuizId)
            .map(mapper::mapToShakerQuiz)
    }
}

