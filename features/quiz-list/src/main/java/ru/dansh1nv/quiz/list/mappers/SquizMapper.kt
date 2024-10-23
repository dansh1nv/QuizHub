package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.orZero
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.GameDateUI
import ru.dansh1nv.quiz.list.models.Location
import ru.dansh1nv.quiz.list.models.Organization
import ru.dansh1nv.quiz.list.models.QuizUI
import ru.dansh1nv.quiz_list_domain.models.GameFormat
import ru.dansh1nv.quiz_list_domain.models.GameType
import ru.dansh1nv.quiz_list_domain.models.SQuiz

internal class SquizMapper(
    private val resourceManager: IResourceManager
) {

    fun mapToQuizUI(quizzes: List<SQuiz>): List<QuizUI> {
        return quizzes.map(::mapToQuizUI)
    }

    fun mapToQuizUI(squiz: SQuiz): QuizUI {
        val gameFormat = squiz.format ?: GameFormat.OFFLINE
        return with(squiz) {
            QuizUI(
                id = id.orZero(),
                city = city.orEmpty(),
                formattedDate = GameDateUI(
                    date = gameDate?.dateTime,
                    dateText = "${gameDate?.day} ${gameDate?.month}",
                    timeWithDay = buildString {
                        gameDate?.time?.let { append("$it, ") }
                        gameDate?.dayOfTheWeek?.let { append(it) }
                    }
                ),
                type = type ?: GameType.CLASSIC,
                format = gameFormat,
                theme = theme.orEmpty(),
                status = status.orEmpty(),
                packageNumber = packageNumber.orEmpty(),
                description = description.orEmpty(),
                additionDescription = additionDescription.orEmpty(),
                image = image.orEmpty(),
                place = place.orEmpty(),
                address = address.orEmpty(),
                formatPrice = price.orEmpty(),
                priceAdditionalText = when (gameFormat) {
                    GameFormat.ONLINE -> {
                        resourceManager.getStringById(R.string.quiz_item_price_for_team)
                    }

                    GameFormat.OFFLINE -> {
                        resourceManager.getStringById(R.string.quiz_item_price_for_people)
                    }

                    else -> ""
                },
                organization = Organization.SQUIZ,
                location = Location.EMPTY,
                difficulty = "",
                paymentMethod = "",
            )
        }
    }
}