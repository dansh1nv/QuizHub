package ru.quizHub.quizList.models.item

import ru.quizHub.quizList.models.TagModel
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType

data class QuizUI(
    val id: String,
    val organization: Organization,
    val tag: TagModel,
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
    val isVisible: Boolean = true,
)