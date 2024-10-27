package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.WowQuiz
import ru.dansh1nv.quiz_list_domain.repository.IWowQuizRepository

class WowQuizRepository: IWowQuizRepository {
    override suspend fun getQuizList(): Flow<List<WowQuiz>> {
        TODO("Not yet implemented")
    }
}