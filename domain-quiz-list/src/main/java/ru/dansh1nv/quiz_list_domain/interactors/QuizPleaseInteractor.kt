package ru.dansh1nv.quiz_list_domain.interactors

import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository

class QuizPleaseInteractor(
    private val repository: IQuizPleaseRepository,
) {
    suspend fun getQuizList(
        cityId: Int,
        pageNumber: Int,
        pageSize: Int,
    ) = repository.getQuizList(
        cityId = cityId,
        pageNumber = pageNumber,
        pageSize = pageSize
    )
}