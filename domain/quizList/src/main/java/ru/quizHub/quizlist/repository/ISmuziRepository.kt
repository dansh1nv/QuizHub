package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.Smuzi
import ru.quizHub.quizlist.models.common.City

interface ISmuziRepository {
    fun getQuizList(city: City): Flow<List<Smuzi>>
}
