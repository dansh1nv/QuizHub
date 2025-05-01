package ru.dansh1nv.quiz.list.presentation

import ru.dansh1nv.core.presentation.SideEffect

internal sealed class QuizListSideEffect: SideEffect {
    data object NetworkError : QuizListSideEffect()
    data class NavigateQuizDetails(val quizId: String) : QuizListSideEffect()
}