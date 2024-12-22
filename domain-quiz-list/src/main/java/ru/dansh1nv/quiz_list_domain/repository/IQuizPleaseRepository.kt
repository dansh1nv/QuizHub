package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.QuizPlease

interface IQuizPleaseRepository {

    suspend fun getQuizList(
        cityId: Long,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPlease>>

}