package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.WowQuiz

interface IWowQuizRepository {

    fun getQuizList() : Flow<List<ru.quizHub.quizlist.models.WowQuiz>>

}