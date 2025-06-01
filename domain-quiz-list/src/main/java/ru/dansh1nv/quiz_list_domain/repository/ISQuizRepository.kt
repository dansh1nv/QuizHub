package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.SQuiz

interface ISQuizRepository {

    suspend fun getAllQuizzes(cityId: Long): Flow<List<SQuiz>>

}