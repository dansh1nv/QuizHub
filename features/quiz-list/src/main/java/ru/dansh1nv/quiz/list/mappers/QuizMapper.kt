package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.Quiz
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.RudaGames
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz

internal class QuizMapper(
    private val squizMapper: SquizMapper,
    private val quizPleaseMapper: QuizPleaseMapper,
    private val shakerQuizMapper: ShakerQuizMapper,
    private val rudaGamesMapper: RudaGamesMapper,
) {
    fun mapToQuizUI(quiz: Quiz): QuizUI {
        return when (quiz) {
            is QuizPlease -> quizPleaseMapper.mapToQuizUI(quiz)
            is SQuiz -> squizMapper.mapToQuizUI(quiz)
            is ShakerQuiz -> shakerQuizMapper.mapToQuizUI(quiz)
            is RudaGames -> rudaGamesMapper.mapToQuizUI(quiz)
        }
    }
}