package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.quizHub.quizList.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.quizHub.quizList.mappers.RudaGamesDataMapper
import ru.quizHub.quizlist.models.RudaGames
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IRudaGamesRepository

class RudaGamesRepository(
    private val remoteDataSource: RudaGamesRemoteDataSource,
    private val mapper: RudaGamesDataMapper
) : IRudaGamesRepository {
    override suspend fun getQuizList(city: City): Flow<List<RudaGames>> {
        return remoteDataSource.getQuizList(city.rudaGamesId)
            .map(mapper::mapToRudaGames)
    }
}