package ru.quizHub.quizlist.models

import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.Location
import ru.quizHub.quizlist.models.common.PaymentMethod

data class QuizPlease(
    val id: String?,
    val title: String?,
    val packageNumber: String?,
    val description: String?,
    val image: String?,
    val gameFormat: GameFormat?,
    val datetime: String?,
    val formatDate: GameDate?,
    val price: Int?,
    val formatPrice: String?,
    val location: Location?,
    val difficulty: String?,
    val status: Status?,
    val paymentMethod: PaymentMethod?,
) : Quiz()
