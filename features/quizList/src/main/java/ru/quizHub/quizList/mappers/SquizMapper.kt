package ru.quizHub.quizList.mappers

import ru.quizHub.quizList.models.TagModel
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType

internal class SquizMapper(
    private val commonMapper: CommonMapper,
) {

    companion object {
        private const val MIN_MEMBERS_COUNT = 2
        private const val MAX_MEMBERS_COUNT = 8
    }

    fun mapToQuizUI(squiz: ru.quizHub.quizlist.models.SQuiz): QuizUI {
        val gameFormat = squiz.format ?: GameFormat.OFFLINE
        return with(squiz) {
            QuizUI(
                id = id.orEmpty(),
                formattedDate = gameDate?.let(commonMapper::mapToGameDateUI),
                type = type ?: GameType.CLASSIC,
                tag = TagModel.SQUIZ,
                format = gameFormat,
                theme = theme.orEmpty(),
                status = status?.let(commonMapper::mapToStatusUI),
                packageNumber = packageNumber.orEmpty(),
                description = description?.trim().orEmpty(),
                additionDescription = additionDescription.orEmpty(),
                image = image.orEmpty(),
                formatPrice = price.orEmpty(),
                priceAdditionalText = commonMapper.mapPriceAdditionalText(gameFormat),
                organization = Organization.SQUIZ,
                location = location?.let { model ->
                    commonMapper.mapLocationUI(model, gameFormat)
                },
                teamSize = commonMapper.mapTeamSizeUI(
                    minMembersCount = MIN_MEMBERS_COUNT,
                    maxMemberCount = MAX_MEMBERS_COUNT
                ),
                difficulty = squiz.difficult.orEmpty(),
                paymentMethod = "",
            )
        }
    }
}