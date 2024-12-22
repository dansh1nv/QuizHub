package ru.dansh1nv.quiz_list_domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import ru.dansh1nv.quiz_list_domain.models.Quiz

class QuizListInteractor(
    private val squizInteractor: SquizInteractor,
    private val quizPleaseInteractor: QuizPleaseInteractor,
    private val shakerQuizInteractor: ShakerQuizInteractor,
) {

    suspend fun getAllQuizList(cityId: Long): Flow<List<Quiz>> {
        return merge(
            squizInteractor.getQuizList(),
            quizPleaseInteractor.getQuizList(
                cityId = cityId,
                pageNumber = 1,
                pageSize = 100
            ),
            shakerQuizInteractor.fetchQuizzes(),
        )
    }
}