package ru.quizHub.quizList.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizlist.repository.IWowQuizRepository

class WowQuizRepository: IWowQuizRepository {
    override fun getQuizList(): Flow<List<WowQuiz>> = flowOf()
}