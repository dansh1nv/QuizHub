package ru.dansh1nv.quiz.data.datasource.squiz

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quizapi.api.SquizApi
import ru.dansh1nv.quizapi.model.squiz.SquizDTO

class SquizRemoteDataSource(
    private val api: SquizApi,
) {
    fun getAllQuizzes(cityId: Long): Flow<List<SquizDTO>> = api.getQuizzes(cityId = cityId)
}