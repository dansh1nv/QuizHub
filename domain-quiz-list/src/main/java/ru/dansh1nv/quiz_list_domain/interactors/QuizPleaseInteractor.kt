package ru.dansh1nv.quiz_list_domain.interactors

import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository

class QuizPleaseInteractor(
    private val repository: IQuizPleaseRepository,
) {
    suspend fun getQuizList() = repository.getQuizList()
}