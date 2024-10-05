package ru.dansh1nv.quiz.list.models

data class QuizPleaseUI (
    val id: Long,
    val theme: String,
    val packageNumber: String,
    val description: String,
    val image: String,
    val gameFormat: String,
    val gameDate: GameDateUI,
    val formatPrice: String,
    val place: String,
    val address: String,
    val city: String,
    val location: Location,
    val difficulty: String,
    val status: String,
    val paymentMethod: String,
) : QuizUI()

sealed class QuizUI