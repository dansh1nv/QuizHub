package ru.dansh1nv.quiz.list.models

import ru.dansh1nv.quiz_list_domain.models.GameFormat
import ru.dansh1nv.quiz_list_domain.models.GameType

data class QuizUI(
    val id: Long,
    val organization: Organization,
    val theme: String,
    val packageNumber: String,
    val description: String,
    val additionDescription: String,
    val image: String,
    val format: GameFormat,
    val type: GameType,
    val formattedDate: GameDateUI,
    val formatPrice: String,
    val priceAdditionalText: String,
    val place: String,
    val address: String,
    val city: String,
    val location: Location,
    val difficulty: String,
    val status: String,
    val paymentMethod: String,
)