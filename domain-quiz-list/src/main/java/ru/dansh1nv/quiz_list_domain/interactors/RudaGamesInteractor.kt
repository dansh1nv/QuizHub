package ru.dansh1nv.quiz_list_domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.RudaGames
import ru.dansh1nv.quiz_list_domain.repository.IRudaGamesRepository

class RudaGamesInteractor(
    private val repository: IRudaGamesRepository,
) {

    suspend fun fetchQuizzes() : Flow<List<RudaGames>> = repository.fetchQuizzes()

}