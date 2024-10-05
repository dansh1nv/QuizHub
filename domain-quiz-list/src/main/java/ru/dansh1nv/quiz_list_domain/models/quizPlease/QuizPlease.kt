package ru.dansh1nv.quiz_list_domain.models.quizPlease

import ru.dansh1nv.quiz_list_domain.models.Quiz

data class QuizPlease(
    val id: Long?,
    val title: String?,
    val packageNumber: String?,
    val description: String?,
    val image: String?,
    val gameFormat: GameFormat?,
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
    val difficulty: String?, //<div class=\"badge-difficulty__title badge-difficulty__title_registration\"
    val status: Status?,
    val paymentMethod: PaymentMethod?,
): Quiz
