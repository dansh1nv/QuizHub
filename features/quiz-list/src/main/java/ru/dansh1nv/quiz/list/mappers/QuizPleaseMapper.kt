package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.utils.localeDate.localeDay
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.models.item.GameDateUI
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.common.GameDate

internal class QuizPleaseMapper(
    private val resourceManager: IResourceManager,
    private val commonMapper: CommonMapper,
) {

    fun mapToQuizUI(entities: List<QuizPlease>): List<QuizUI> {
        return entities.map(::mapToQuizUI)
    }

    fun mapToQuizUI(entity: QuizPlease): QuizUI {
        return QuizUI(
            id = entity.id.orEmpty(),
            theme = buildString {
                entity.title?.let {
                    append("$it, ")
                }
                entity.packageNumber?.let { append(it) }
            },
            formattedDate = entity.formatDate?.let(::mapGameDateUI),
            formatPrice = entity.formatPrice.orEmpty(),
            description = entity.description.orEmpty(),
            image = entity.image.orEmpty(),
            difficulty = entity.difficulty.orEmpty(),
            location = entity.location?.let(commonMapper::mapLocationUI),
            //TODO: Уточнить по правилам
            teamSize = commonMapper.mapTeamSizeUI(minMembersCount = 2, maxMemberCount = 9),
            format = entity.gameFormat ?: GameFormat.OFFLINE,
            packageNumber = entity.packageNumber.orEmpty(),
            paymentMethod = entity.paymentMethod?.title.orEmpty(),
            status = entity.status?.let (commonMapper::mapToStatusUI),
            organization = Organization.QUIZ_PLEASE,
            additionDescription = "",
            priceAdditionalText = entity.gameFormat
                ?.let(commonMapper::mapPriceAdditionalText).orEmpty(),
            type = GameType.CLASSIC,
        )
    }

    private fun mapGameDateUI(gameDate: GameDate) : GameDateUI {
        return GameDateUI(
            date = gameDate.dateTime,
            dateText = "${gameDate.day} ${gameDate.month}",
            timeWithDay = buildString {
                append("${gameDate.time}, ")
                append(localeDay(gameDate.dateTime.dayOfWeek))
            },
        )
    }
}