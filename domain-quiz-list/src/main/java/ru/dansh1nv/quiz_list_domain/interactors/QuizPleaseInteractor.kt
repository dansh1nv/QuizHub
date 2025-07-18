package ru.dansh1nv.quiz_list_domain.interactors

import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository

class QuizPleaseInteractor(
    private val repository: IQuizPleaseRepository,
) {
    suspend fun getQuizList(
        cityId: City,
        pageNumber: Int,
        pageSize: Int,
    ) = repository.getQuizList(
        city = cityId,
        pageNumber = pageNumber,
        pageSize = pageSize
    )
}