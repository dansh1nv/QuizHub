package ru.quizHub.quizlist.interactors

import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.IQuizPleaseRepository

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