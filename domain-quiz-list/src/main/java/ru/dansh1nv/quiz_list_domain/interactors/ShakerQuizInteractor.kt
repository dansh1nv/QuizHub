package ru.dansh1nv.quiz_list_domain.interactors

import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.repository.IShakerQuizRepository

class ShakerQuizInteractor(
    private val repository: IShakerQuizRepository
) {

    suspend fun fetchQuizzes(cityId: City) = repository.fetchQuizzes(cityId)

}