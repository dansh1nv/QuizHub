package ru.quizHub.quizList.mappers

import ru.quizHub.common.Currency
import ru.quizHub.quizList.models.TagModel
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizlist.models.Smuzi
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType

class SmuziMapper(
    private val commonMapper: CommonMapper,
) {

    fun mapToQuizUI(entity: Smuzi): QuizUI {
        val gameFormat = GameFormat.OFFLINE
        return QuizUI(
            id = entity.id.orEmpty(),
            organization = Organization.SMUZI,
            tag = TagModel.SMUZI,
            theme = entity.title.orEmpty(),
            packageNumber = "",
            description = entity.description?.trim().orEmpty(),
            additionDescription = "",
            image = entity.image.orEmpty(),
            format = gameFormat,
            type = GameType.THEMATIC,
            teamSize = null,
            formattedDate = entity.eventTime?.let(commonMapper::mapToGameDateUI),
            formatPrice = mapPrice(entity.price),
            priceAdditionalText = commonMapper.mapPriceAdditionalText(gameFormat),
            location = entity.location?.let { model ->
                commonMapper.mapLocationUI(model, gameFormat)
            },
            difficulty = "",
            status = null,
            paymentMethod = "",
        )
    }

    private fun mapPrice(price: Int?): String {
        if (price == null) return ""
        return "$price${Currency.RUB.symbol}"
    }
}
