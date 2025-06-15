package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.Quiz
import ru.dansh1nv.quiz_list_domain.models.common.City

interface IQuizListRepository {
    fun getAllQuizList(city: City): Flow<List<Quiz>>
}