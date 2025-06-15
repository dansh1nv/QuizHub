package ru.dansh1nv.quiz.data.models

import ru.dansh1nv.quizapi.model.quizplease.StatusDTO

data class QuizDTO(
    val id: Long?,
    val title: String?,
    val packageNumber: String?,
    val description: String?,
    val image: String?,
    val gameFormat: Int?,
    val datetime: String?,
    val formatDate: String?,
    val formatTime: String?,
    val price: Int?,
    val formatPrice: String?,
    val location: String?,
    val address: String?,
    val city: String?,
    val latitude: String?,
    val longitude: String?,
    val difficulty: String?,
    val status: StatusDTO?,
    val paymentMethod: Int?,
    val organization: String?,
)