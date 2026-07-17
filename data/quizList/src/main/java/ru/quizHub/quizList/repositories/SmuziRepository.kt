package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.quizHub.quizList.datasource.smuzi.SmuziRemoteDataSource
import ru.quizHub.quizList.mappers.SmuziDataMapper
import ru.quizHub.quizlist.models.Smuzi
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.ISmuziRepository

class SmuziRepository(
    private val remoteDataSource: SmuziRemoteDataSource,
    private val mapper: SmuziDataMapper,
) : ISmuziRepository {

    override fun getQuizList(city: City): Flow<List<Smuzi>> {
        return remoteDataSource
            .getQuizList(storePartId = city.smuziStorePartId)
            .map(mapper::mapToSmuzi)
    }
}