package ru.quizHub.quizList.datasource.rudaGames

import ru.quizHub.quizApi.api.RudaGamesApi

class RudaGamesRemoteDataSource(
    private val api: RudaGamesApi
) {
    fun getQuizList(cityId: Int?) = api.getQuizzes(cityId)
}