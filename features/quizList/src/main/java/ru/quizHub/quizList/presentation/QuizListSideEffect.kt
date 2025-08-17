package ru.quizHub.quizList.presentation

import ru.quizHub.core.presentation.SideEffect

internal sealed class QuizListSideEffect: SideEffect {
    data object NetworkError : QuizListSideEffect()
    data class NavigateQuizDetails(val quizId: String) : QuizListSideEffect()
}