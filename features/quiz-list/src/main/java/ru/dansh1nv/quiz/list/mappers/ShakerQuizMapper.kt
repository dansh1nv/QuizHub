package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.orZero
import ru.dansh1nv.common.utils.localeDate.localeDay
import ru.dansh1nv.quiz.list.models.item.GameDateUI
import ru.dansh1nv.quiz.list.models.item.Organization
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.quiz_list_domain.models.common.GameDate
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.GameType

class ShakerQuizMapper(
    private val commonMapper: CommonMapper,
) {

    companion object {
        private const val ABOUT_ALL = "ОБОВСЁМ"
    }

    fun mapToQuizUI(entity: ShakerQuiz): QuizUI {
        return QuizUI(
            id = entity.id.orEmpty(),
            organization = Organization.SHAKER_QUIZ,
            theme = buildString {
                entity.theme?.let(::append)
                append(" #")
                entity.packageNumber?.let(::append)
            },
            packageNumber = entity.packageNumber.orEmpty(),
            description = entity.shortDescription.orEmpty(),
            additionDescription = "",
            image = entity.image.orEmpty(),
            //У Шейкера только офлайн игры
            format = GameFormat.OFFLINE,
            type = mapGameType(entity.theme.orEmpty()),
            formattedDate = entity.eventTime?.let(::mapToGameDateUI),
            formatPrice = mapPrice(entity),
            priceAdditionalText = commonMapper.mapPriceAdditionalText(GameFormat.OFFLINE),
            difficulty = "",
            status = entity.status?.let(commonMapper::mapToStatusUI),
            paymentMethod = "",
            teamSize = commonMapper.mapTeamSizeUI(
                minMembersCount = entity.minMembersCount.orZero(),
                maxMemberCount = entity.maxMembersCount.orZero()
            ),
            location = entity.location?.let(commonMapper::mapLocationUI),
        )
    }

    private fun mapGameType(theme: String): GameType {
        return if (theme.contains(ABOUT_ALL)) {
            GameType.CLASSIC
        } else {
            GameType.THEMATIC
        }
    }

    private fun mapToGameDateUI(gameDate: GameDate): GameDateUI {
        return GameDateUI(
            date = gameDate.dateTime,
            dateText = "${gameDate.day} ${gameDate.month}",
            timeWithDay = buildString {
                append(gameDate.time)
                append(", ")
                append(localeDay(gameDate.dateTime.dayOfWeek))
            }
        )
    }

    private fun mapPrice(entity: ShakerQuiz): String {
        return buildString {
            entity.price?.let(::append)
            entity.currency?.let(::append)
        }
    }

}