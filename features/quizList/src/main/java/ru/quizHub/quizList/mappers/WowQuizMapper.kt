package ru.quizHub.quizList.mappers

import ru.quizHub.common.Currency
import ru.quizHub.quizList.models.TagModel
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType

class WowQuizMapper(
    private val commonMapper: CommonMapper,
) {

    private companion object {
        const val CLASSIC_THEME = "Классика"
    }

    fun mapToQuizUI(entity: WowQuiz): QuizUI {
        val gameFormat = GameFormat.OFFLINE
        return QuizUI(
            id = entity.id.orEmpty(),
            organization = Organization.WOW_QUIZ,
            tag = TagModel.WOW_QUIZ,
            theme = entity.title.orEmpty(),
            packageNumber = entity.template.orEmpty(),
            description = entity.shortDescription?.trim().orEmpty()
                .ifBlank { entity.description?.trim().orEmpty() },
            additionDescription = "",
            image = entity.image.orEmpty(),
            format = gameFormat,
            type = mapGameType(entity.theme.orEmpty()),
            teamSize = null,
            formattedDate = entity.eventTime?.let(commonMapper::mapToGameDateUI),
            formatPrice = mapPrice(entity),
            priceAdditionalText = commonMapper.mapPriceAdditionalText(gameFormat),
            location = entity.location?.let { model ->
                commonMapper.mapLocationUI(model, gameFormat)
            },
            difficulty = "",
            status = entity.status?.let(commonMapper::mapToStatusUI),
            paymentMethod = "",
        )
    }

    private fun mapGameType(theme: String): GameType {
        return if (theme.contains(CLASSIC_THEME, ignoreCase = true)) {
            GameType.CLASSIC
        } else {
            GameType.THEMATIC
        }
    }

    private fun mapPrice(entity: WowQuiz): String {
        return buildString {
            entity.price?.let(::append)
            entity.currency?.let {
                if (it.equals(Currency.RUB.id, ignoreCase = true)) {
                    append(Currency.RUB.symbol)
                } else {
                    append(it)
                }
            }
        }
    }
}
