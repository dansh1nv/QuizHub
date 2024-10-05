package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.quizPlease.QuizPlease

interface IQuizPleaseRepository {

    suspend fun getQuizList(): Flow<List<QuizPlease>>

}