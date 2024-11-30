package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.rudaGames.RudaGamesRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.RudaGamesMapper
import ru.dansh1nv.quiz_list_domain.models.RudaGames
import ru.dansh1nv.quiz_list_domain.repository.IRudaGamesRepository

class RudaGamesRepository(
//    private val localDataSource: RudaGamesLocalDataSource,
    private val remoteDataSource: RudaGamesRemoteDataSource,
    private val mapper: RudaGamesMapper,
) : IRudaGamesRepository {

    override suspend fun fetchQuizzes(): Flow<List<RudaGames>> =
        remoteDataSource.fetchQuizzes().map(mapper::mapToRudaGames)

}