package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.common.City

interface IQuizListRepository {
    /**
     * Получает список квизов для города (stale-while-revalidate).
     * @param city выбранный город
     * @param forceRefresh если true — после кэша всегда идёт сеть и обновление кэша;
     * иначе сеть вызывается только при пустом или протухшем кэше (TTL 45 мин)
     */
    fun getAllQuizList(city: City, forceRefresh: Boolean = false): Flow<List<Quiz>>
}
