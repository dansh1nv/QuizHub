package ru.dansh1nv.quiz.data.datasource.squiz

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quizapi.api.SquizApi
import ru.dansh1nv.quizapi.model.squiz.SquizDTO

class SquizRemoteDataSource(
    private val api: SquizApi,
) {
    suspend fun getAllQuizzes(): Flow<List<SquizDTO>> = api.getQuizzes()
}