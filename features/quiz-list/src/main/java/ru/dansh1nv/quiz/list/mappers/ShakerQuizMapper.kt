package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.StringDividerType
import ru.dansh1nv.common.formatStringsWithDividerPoints
import ru.dansh1nv.common.orZero
import ru.dansh1nv.quiz.list.models.TagModel
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType

class ShakerQuizMapper(
    private val commonMapper: CommonMapper,
) {

    companion object {
        private const val ABOUT_ALL = "ОБОВСЁМ"
    }

    fun mapToQuizUI(entity: ShakerQuiz): QuizUI {
        //У Шейкера только офлайн игры
        val gameFormat = GameFormat.OFFLINE
        return QuizUI(
            id = entity.id.orEmpty(),
            organization = Organization.SHAKER_QUIZ,
            theme = formatStringsWithDividerPoints(
                arrayOf(entity.theme, entity.packageNumber),
                StringDividerType.NumberWithSpace
            ),
            tag = TagModel.SHAKER_QUIZ,
            packageNumber = entity.packageNumber.orEmpty(),
            description = entity.shortDescription.orEmpty(),
            additionDescription = "",
            image = entity.image.orEmpty(),
            format = gameFormat,
            type = mapGameType(entity.theme.orEmpty()),
            formattedDate = entity.eventTime?.let(commonMapper::mapToGameDateUI),
            formatPrice = mapPrice(entity),
            priceAdditionalText = commonMapper.mapPriceAdditionalText(GameFormat.OFFLINE),
            difficulty = "",
            status = entity.status?.let(commonMapper::mapToStatusUI),
            paymentMethod = "",
            teamSize = commonMapper.mapTeamSizeUI(
                minMembersCount = entity.minMembersCount.orZero(),
                maxMemberCount = entity.maxMembersCount.orZero()
            ),
            location = entity.location?.let { model ->
                commonMapper.mapLocationUI(model, gameFormat)
            },
        )
    }

    private fun mapGameType(theme: String): GameType {
        return if (theme.contains(ABOUT_ALL)) {
            GameType.CLASSIC
        } else {
            GameType.THEMATIC
        }
    }

    private fun mapPrice(entity: ShakerQuiz): String {
        return buildString {
            entity.price?.let(::append)
            entity.currency?.let(::append)
        }
    }

}