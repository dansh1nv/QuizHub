package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.models.common.City

interface ISQuizRepository {

    suspend fun getAllQuizzes(cityId: City): Flow<List<SQuiz>>

}