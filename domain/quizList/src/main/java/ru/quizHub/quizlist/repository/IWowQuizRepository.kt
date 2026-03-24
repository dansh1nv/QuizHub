package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizlist.models.common.City

interface IWowQuizRepository {

    fun getQuizList(
        city: City,
        page: Int = 1,
        upcoming: Int = 1,
    ): Flow<List<WowQuiz>>
}