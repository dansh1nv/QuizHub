package ru.quizHub.quizList.datasource.squiz

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizApi.api.SquizApi
import ru.quizHub.quizApi.model.squiz.SquizDTO

class SquizRemoteDataSource(
    private val api: SquizApi,
) {
    fun getQuizList(cityId: Long?): Flow<List<SquizDTO>> = api.getQuizzes(cityId = cityId)
}