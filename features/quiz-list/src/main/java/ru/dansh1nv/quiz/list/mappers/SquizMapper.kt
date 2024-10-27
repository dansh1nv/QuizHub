package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.utils.localeDate.localeDay
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.models.item.GameDateUI
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType

internal class SquizMapper(
    private val resourceManager: IResourceManager,
    private val commonMapper: CommonMapper,
) {

    fun mapToQuizUI(quizzes: List<SQuiz>): List<QuizUI> {
        return quizzes.map(::mapToQuizUI)
    }

    fun mapToQuizUI(squiz: SQuiz): QuizUI {
        val gameFormat = squiz.format ?: GameFormat.OFFLINE
        return with(squiz) {
            QuizUI(
                id = id.orEmpty(),
                formattedDate = GameDateUI(
                    date = gameDate?.dateTime,
                    dateText = "${gameDate?.day} ${gameDate?.month}",
                    timeWithDay = buildString {
                        gameDate?.time?.let { append("$it, ") }
                        gameDate?.dateTime?.dayOfWeek?.let { append(localeDay(it)) }
                    }
                ),
                type = type ?: GameType.CLASSIC,
                format = gameFormat,
                theme = theme.orEmpty(),
                status = status?.let(commonMapper::mapToStatusUI),
                packageNumber = packageNumber.orEmpty(),
                description = description.orEmpty(),
                additionDescription = additionDescription.orEmpty(),
                image = image.orEmpty(),
                formatPrice = price.orEmpty(),
                priceAdditionalText = commonMapper.mapPriceAdditionalText(gameFormat),
                organization = Organization.SQUIZ,
                location = location?.let(commonMapper::mapLocationUI),
                teamSize = commonMapper.mapTeamSizeUI(minMembersCount = 2, maxMemberCount = 8),
                difficulty = "",
                paymentMethod = "",
            )
        }
    }
}