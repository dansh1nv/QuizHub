package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.StringDividerType
import ru.dansh1nv.common.formatStringsWithDividerPoints
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.models.TagModel
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType

internal class QuizPleaseMapper(
    private val resourceManager: IResourceManager,
    private val commonMapper: CommonMapper,
) {

    companion object {
        private const val SUBSTRING_TEXT = "Будьте внимательны, чтобы не попасть на такой же пакет"
        private const val MIN_MEMBERS_COUNT = 2
        private const val MAX_MEMBERS_COUNT = 9
    }

    fun mapToQuizUI(entities: List<QuizPlease>): List<QuizUI> {
        return entities.map(::mapToQuizUI)
    }

    fun mapToQuizUI(entity: QuizPlease): QuizUI {
        val gameFormat = entity.gameFormat ?: GameFormat.OFFLINE
        return QuizUI(
            id = entity.id.orEmpty(),
            theme = formatStringsWithDividerPoints(
                arrayOf(entity.title, entity.packageNumber),
                StringDividerType.Space
            ),
            tag = TagModel.QUIZ_PLEASE,
            formattedDate = entity.formatDate?.let(commonMapper::mapToGameDateUI),
            formatPrice = entity.formatPrice.orEmpty(),
            description = entity.description?.substringBefore(SUBSTRING_TEXT).orEmpty(),
            image = entity.image.orEmpty(),
            difficulty = entity.difficulty.orEmpty(),
            location = entity.location?.let { model ->
                commonMapper.mapLocationUI(model, gameFormat)
            },
            //TODO: Уточнить по правилам
            teamSize = commonMapper.mapTeamSizeUI(
                minMembersCount = MIN_MEMBERS_COUNT,
                maxMemberCount = MAX_MEMBERS_COUNT
            ),
            format = gameFormat,
            packageNumber = entity.packageNumber.orEmpty(),
            paymentMethod = entity.paymentMethod?.title.orEmpty(),
            status = entity.status?.let(commonMapper::mapToStatusUI),
            organization = Organization.QUIZ_PLEASE,
            additionDescription = "",
            priceAdditionalText = entity.gameFormat
                ?.let(commonMapper::mapPriceAdditionalText).orEmpty(),
            type = GameType.CLASSIC,
        )
    }
}