package ru.dansh1nv.quiz.list.models.item

import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType

data class QuizUI(
    val id: String,
    val organization: Organization,
    val theme: String,
    val packageNumber: String,
    val description: String,
    val additionDescription: String,
    val image: String,
    val format: GameFormat,
    val type: GameType,
    val teamSize: TeamSizeUI?,
    val formattedDate: GameDateUI?,
    val formatPrice: String,
    val priceAdditionalText: String,
    val location: LocationUI?,
    val difficulty: String,
    val status: StatusUI?,
    val paymentMethod: String,
)