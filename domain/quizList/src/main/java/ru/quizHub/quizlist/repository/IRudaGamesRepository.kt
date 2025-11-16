package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.RudaGames
import ru.quizHub.quizlist.models.common.City

interface IRudaGamesRepository {
    suspend fun getQuizList(city: City): Flow<List<RudaGames>>
}