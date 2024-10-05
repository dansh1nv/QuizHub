package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.quizPlease.QuizPleaseRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.QuizPleaseMapper
import ru.dansh1nv.quiz_list_domain.repository.IQuizPleaseRepository

class QuizPleaseRepository(
    private val remoteDataSource: QuizPleaseRemoteDataSource,
    private val mapper: QuizPleaseMapper,
) : IQuizPleaseRepository {
    override suspend fun getQuizList() =
        remoteDataSource.getQuizList()
            .map(mapper::mapToQuiz)
}