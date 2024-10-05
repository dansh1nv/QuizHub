package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.orZero
import ru.dansh1nv.quiz.list.models.GameDateUI
import ru.dansh1nv.quiz.list.models.SQuizUI
import ru.dansh1nv.quiz_list_domain.models.squiz.GameFormat
import ru.dansh1nv.quiz_list_domain.models.squiz.GameType
import ru.dansh1nv.quiz_list_domain.models.squiz.SQuiz

object SquizMapper {

    fun mapToQuizUI(quizzes: List<SQuiz>): List<SQuizUI> {
        return quizzes.map(::mapToQuizUI)
    }

    fun mapToQuizUI(squiz: SQuiz): SQuizUI {
        return with(squiz) {
            SQuizUI(
                id = id.orZero(),
                city = city.orEmpty(),
                gameDate = GameDateUI(
                    dateText = gameDate?.dayWithMonth.orEmpty(),
                    timeWithDay = buildString {
                        gameDate?.time?.let { append("$it, ") }
                        gameDate?.dayOfTheWeek?.let { append(it) }
                    }
                ),
                type = type ?: GameType.UNKNOWN,
                format = format ?: GameFormat.UNKNOWN,
                theme = theme.orEmpty(),
                status = status.orEmpty(),
                packageNumber = packageNumber.orEmpty(),
                description = description.orEmpty(),
                additionDescription = additionDescription.orEmpty(),
                image = image.orEmpty(),
                location = location.orEmpty(),
                address = address.orEmpty(),
                price = price.orEmpty(),
                organization = organization.orEmpty(),
            )
        }
    }
}