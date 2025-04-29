package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.WowQuiz

interface IWowQuizRepository {

    suspend fun getQuizList() : Flow<List<WowQuiz>>

}