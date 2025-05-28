package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.QuizUI

class ActionEventsMapper(private val resourceManager: IResourceManager) {
    fun mapToShareText(quiz: QuizUI): String {
        return buildString {
            append("${resourceManager.getStringById(R.string.join_quiz)} ${quiz.organization}\n\n")
            quiz.formattedDate?.dateText?.let {
                append("${resourceManager.getStringById(R.string.date_quiz)} $it\n")
            }
            quiz.formattedDate?.timeWithDay?.let {
                append("${resourceManager.getStringById(R.string.time_day_quiz)} $it\n")
            }
            quiz.teamSize?.teamSizeText?.let {
                append("${resourceManager.getStringById(R.string.team_size_quiz)} $it\n")
            }
            quiz.location?.address?.let {
                append("${resourceManager.getStringById(R.string.address_quiz)} $it\n")
            }
            quiz.location?.place?.let {
                append("${resourceManager.getStringById(R.string.place_quiz)} $it\n")
            }
            append("${resourceManager.getStringById(R.string.price_quiz)} ${quiz.formatPrice}\n")
        }
    }
}