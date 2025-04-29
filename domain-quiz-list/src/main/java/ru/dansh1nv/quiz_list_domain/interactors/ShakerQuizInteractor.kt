package ru.dansh1nv.quiz_list_domain.interactors

import ru.dansh1nv.quiz_list_domain.repository.IShakerQuizRepository

class ShakerQuizInteractor(
    private val repository: IShakerQuizRepository
) {

    suspend fun fetchQuizzes() = repository.fetchQuizzes()

}