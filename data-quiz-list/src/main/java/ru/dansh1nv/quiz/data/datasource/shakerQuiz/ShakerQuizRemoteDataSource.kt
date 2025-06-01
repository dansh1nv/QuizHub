package ru.dansh1nv.quiz.data.datasource.shakerQuiz

import ru.dansh1nv.quizapi.api.ShakerQuizApi

class ShakerQuizRemoteDataSource(
    private val api: ShakerQuizApi
) {

    fun getQuizList() = api.getQuizzes()

}