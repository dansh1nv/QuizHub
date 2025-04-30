package ru.dansh1nv.quiz.list.presentation

import ru.dansh1nv.core.presentation.SideEffect

sealed class QuizListSideEffect: SideEffect {
    data object NetworkError : QuizListSideEffect()
}