package ru.dansh1nv.quiz.list.presentation

sealed class QuizListSideEffect {
    data object NetworkError : QuizListSideEffect()
}