package ru.quizHub.quizlist.models.common

sealed class Organization {
    data object Squiz : Organization()
    data object ShakerQuiz : Organization()
    data object QuizPlease : Organization()
}