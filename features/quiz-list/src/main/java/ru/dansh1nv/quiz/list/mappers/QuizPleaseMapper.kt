package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.orZero
import ru.dansh1nv.quiz.list.models.Location
import ru.dansh1nv.quiz.list.models.QuizPleaseUI
import ru.dansh1nv.quiz.list.models.GameDateUI
import ru.dansh1nv.quiz_list_domain.models.quizPlease.QuizPlease

object QuizPleaseMapper {

    fun mapToQuizPlease(entities: List<QuizPlease>): List<QuizPleaseUI> {
        return entities.map(::mapToQuizPlease)
    }

    fun mapToQuizPlease(entity: QuizPlease): QuizPleaseUI {
        return QuizPleaseUI(
            id = entity.id.orZero(),
            city = entity.city.orEmpty(),
            theme = buildString {
                entity.title?.let {
                    append("$it, ")
                }
                entity.packageNumber?.let { append(it) }
            },
            gameDate = GameDateUI(
                dateText = entity.formatDate.orEmpty(),
                timeWithDay = entity.formatTime.orEmpty(),
            ),
            formatPrice = entity.formatPrice.orEmpty(),
            description = entity.description.orEmpty(),
            place = entity.location.orEmpty(),
            image = entity.image.orEmpty(),
            address = entity.address.orEmpty(),
            difficulty = entity.difficulty.orEmpty(),
            location = Location(
                latitude = entity.latitude.orEmpty(),
                longitude = entity.longitude.orEmpty(),
                locationText = "${entity.latitude.orEmpty()},${entity.longitude.orEmpty()}"
            ),
            gameFormat = entity.gameFormat?.description.orEmpty(),
            packageNumber = entity.packageNumber.orEmpty(),
            paymentMethod = entity.paymentMethod?.title.orEmpty(),
            status = entity.status?.title.orEmpty(),
        )
    }
}