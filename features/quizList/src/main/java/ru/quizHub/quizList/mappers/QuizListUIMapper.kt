package ru.quizHub.quizList.mappers

import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.RudaGames
import ru.quizHub.quizlist.models.SQuiz
import ru.quizHub.quizlist.models.ShakerQuiz
import ru.quizHub.quizlist.models.Smuzi
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizList.models.item.QuizUI

internal class QuizListUIMapper(
    private val squizMapper: SquizMapper,
    private val quizPleaseMapper: QuizPleaseMapper,
    private val shakerQuizMapper: ShakerQuizMapper,
    private val rudaGamesMapper: RudaGamesMapper,
    private val wowQuizMapper: WowQuizMapper,
    private val smuziMapper: SmuziMapper,
) {

    fun mapToQuizUI(quiz: Quiz): QuizUI = when (quiz) {
        is QuizPlease -> quizPleaseMapper.mapToQuizUI(quiz)
        is SQuiz -> squizMapper.mapToQuizUI(quiz)
        is ShakerQuiz -> shakerQuizMapper.mapToQuizUI(quiz)
        is RudaGames -> rudaGamesMapper.mapToQuizUI(quiz)
        is WowQuiz -> wowQuizMapper.mapToQuizUI(quiz)
        is Smuzi -> smuziMapper.mapToQuizUI(quiz)
    }
}
