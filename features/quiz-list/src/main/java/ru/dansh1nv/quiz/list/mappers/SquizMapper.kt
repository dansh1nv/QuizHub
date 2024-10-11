package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.orZero
import ru.dansh1nv.common.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.GameDateUI
import ru.dansh1nv.quiz.list.models.SQuizUI
import ru.dansh1nv.quiz_list_domain.models.GameFormat
import ru.dansh1nv.quiz_list_domain.models.GameType
import ru.dansh1nv.quiz_list_domain.models.SQuiz

class SquizMapper(
    private val resourceManager: IResourceManager
) {

    fun mapToQuizUI(quizzes: List<SQuiz>): List<SQuizUI> {
        return quizzes.map(::mapToQuizUI)
    }

    fun mapToQuizUI(squiz: SQuiz): SQuizUI {
        val gameFormat = squiz.format ?: GameFormat.OFFLINE
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
                type = type ?: GameType.CLASSIC,
                format = gameFormat,
                theme = theme.orEmpty(),
                status = status.orEmpty(),
                packageNumber = packageNumber.orEmpty(),
                description = description.orEmpty(),
                additionDescription = additionDescription.orEmpty(),
                image = image.orEmpty(),
                location = location.orEmpty(),
                address = address.orEmpty(),
                price = price.orEmpty(),
                priceAdditionalText = when (gameFormat) {
                    GameFormat.ONLINE -> {
                        resourceManager.getStringById(R.string.quiz_item_price_for_team)
                    }

                    GameFormat.OFFLINE -> {
                        resourceManager.getStringById(R.string.quiz_item_price_for_people)
                    }

                    else -> ""
                },
                organization = organization.orEmpty(),
            )
        }
    }
}