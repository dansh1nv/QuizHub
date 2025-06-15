package ru.dansh1nv.quiz_list_domain.models.common

sealed class Organization {
    data object Squiz : Organization()
    data object ShakerQuiz : Organization()
    data object QuizPlease : Organization()
}