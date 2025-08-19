package ru.quizHub.quizList.datasource.shakerQuiz

import ru.quizHub.quizApi.api.ShakerQuizApi

class ShakerQuizRemoteDataSource(
    private val api: ShakerQuizApi
) {

    fun getQuizList(cityId: String?) = api.getQuizzes(cityId)

}