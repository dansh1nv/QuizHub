package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City

interface IQuizListRepository {
    /**
     * Получает список квизов.
     * @param city выбранный город
     * @param forceRefresh если true — принудительно запрашивает данные из сети и обновляет кэш
     */
    fun getAllQuizList(city: City, forceRefresh: Boolean = false): Flow<List<Quiz>>
}
