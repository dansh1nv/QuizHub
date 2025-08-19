package ru.quizHub.quizlist.interactors

import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IShakerQuizRepository

class ShakerQuizInteractor(
    private val repository: IShakerQuizRepository
) {

    suspend fun fetchQuizzes(cityId: City) = repository.fetchQuizzes(cityId)

}