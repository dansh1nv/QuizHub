package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.dansh1nv.quiz_list_domain.models.WowQuiz
import ru.dansh1nv.quiz_list_domain.repository.IWowQuizRepository

class WowQuizRepository: IWowQuizRepository {
    override fun getQuizList(): Flow<List<WowQuiz>> = flowOf()
}