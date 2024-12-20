package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.models.TagModel
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.SQuiz
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType

internal class SquizMapper(
    private val resourceManager: IResourceManager,
    private val commonMapper: CommonMapper,
) {

    companion object {
        private const val MIN_MEMBERS_COUNT = 2
        private const val MAX_MEMBERS_COUNT = 8
    }

    fun mapToQuizUI(quizzes: List<SQuiz>): List<QuizUI> {
        return quizzes.map(::mapToQuizUI)
    }

    fun mapToQuizUI(squiz: SQuiz): QuizUI {
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
                description = description.orEmpty(),
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