package ru.dansh1nv.quiz.data.datasource.rudaGames

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quizapi.api.RudaGamesApi
import ru.dansh1nv.quizapi.model.rudagames.EventDTO

class RudaGamesRemoteDataSource(
    private val api: RudaGamesApi
) {
    suspend fun fetchQuizzes(): Flow<List<EventDTO>> = api.getQuizzes()
}