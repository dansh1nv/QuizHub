package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.ShareEvent

object ShareEventMapper {
    fun mapToShareEvent(quiz: QuizUI): ShareEvent {
        return ShareEvent(
            title = quiz.organization.toString(),
            date = quiz.formattedDate?.dateText,
            time = quiz.formattedDate?.timeWithDay.toString(),
            teamSize = quiz.teamSize?.teamSizeText,
            address = quiz.location?.address.toString(),
            place = quiz.location?.place.toString(),
            price = quiz.formatPrice
        )
    }
}