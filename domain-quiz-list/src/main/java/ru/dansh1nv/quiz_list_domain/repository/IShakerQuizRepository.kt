package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.quiz_list_domain.models.common.City

interface IShakerQuizRepository {
    fun fetchQuizzes(city: City): Flow<List<ShakerQuiz>>
}