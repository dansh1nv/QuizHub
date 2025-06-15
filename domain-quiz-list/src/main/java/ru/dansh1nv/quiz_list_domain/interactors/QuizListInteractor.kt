package ru.dansh1nv.quiz_list_domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.dansh1nv.quiz_list_domain.models.Quiz
import ru.dansh1nv.quiz_list_domain.models.common.City

class QuizListInteractor(
    private val squizInteractor: SquizInteractor,
    private val quizPleaseInteractor: QuizPleaseInteractor,
    private val shakerQuizInteractor: ShakerQuizInteractor,
) {

    suspend fun getAllQuizList(cityId: City): Flow<List<Quiz>> {
        return combine(
            squizInteractor.getQuizList(cityId),
            quizPleaseInteractor.getQuizList(
                cityId = cityId,
                pageNumber = 1,
                pageSize = 100
            ),
            shakerQuizInteractor.fetchQuizzes(cityId),
        ) { squizList, quizPleaseList, shakerQuizList ->
            listOf(
                squizList,
                quizPleaseList,
                shakerQuizList
            ).flatten()
        }
    }
}