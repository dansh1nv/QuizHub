package ru.quizHub.quizlist.interactors

import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IRudaGamesRepository

class RudaGamesInteractor(
    private val repository: IRudaGamesRepository
) {
    suspend fun getQuizList(cityId: City) = repository.getQuizList(city = cityId)
}