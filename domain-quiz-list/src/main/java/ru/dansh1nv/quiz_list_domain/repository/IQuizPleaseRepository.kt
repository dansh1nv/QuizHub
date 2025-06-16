package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.common.City

interface IQuizPleaseRepository {

    suspend fun getQuizList(
        cityId: City,
        pageNumber: Int,
        pageSize: Int,
    ): Flow<List<QuizPlease>>

}