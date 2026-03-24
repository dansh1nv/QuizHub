package ru.quizHub.quizList.datasource.wowQuiz

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.quizHub.quizApi.api.WowQuizApi
import ru.quizHub.quizApi.model.wowquiz.WowGameDTO

class WowQuizRemoteDataSource(
    private val api: WowQuizApi,
) {
    fun getQuizList(
        domain: String?,
        page: Int,
        upcoming: Int,
    ): Flow<List<WowGameDTO>> =
        if (domain.isNullOrBlank()) {
            flowOf(emptyList())
        } else {
            api.getGames(domain = domain, page = page, upcoming = upcoming)
        }
}
