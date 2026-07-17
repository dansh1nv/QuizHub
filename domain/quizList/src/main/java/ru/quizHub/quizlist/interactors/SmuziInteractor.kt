package ru.quizHub.quizlist.interactors

import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.ISmuziRepository

class SmuziInteractor(
    private val repository: ISmuziRepository,
) {
    fun getQuizList(city: City) = repository.getQuizList(city = city)
}
