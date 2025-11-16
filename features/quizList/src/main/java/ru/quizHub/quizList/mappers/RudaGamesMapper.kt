package ru.quizHub.quizList.mappers

import ru.quizHub.common.orZero
import ru.quizHub.quizList.models.TagModel
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizlist.models.RudaGames
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType

class RudaGamesMapper(
    private val commonMapper: CommonMapper
) {

    private companion object {
        const val MOZGOBOINYA = "Мозгобойня"
        const val TUTS_TUTS_QUIZ = "Туц Туц Quiz"
    }

    fun mapToQuizUI(entity: RudaGames): QuizUI {
        //У rudaGames только офлайн игры
        val gameFormat = GameFormat.OFFLINE
        return QuizUI(
            id = entity.id.orEmpty(),
            organization = Organization.RUDA_GAMES,
            tag = TagModel.RUDA_GAMES,
            theme = uniqueGameName(entity.title.orEmpty()),
            packageNumber = extractPackageNumber(entity.title.orEmpty()),
            description = entity.description.orEmpty(),
            additionDescription = "",
            image = entity.image.orEmpty(),
            format = gameFormat,
            type = entity.gameType ?: GameType.THEMATIC,
            teamSize = commonMapper.mapTeamSizeUI(
                minMembersCount = entity.minMembersCount.orZero(),
                maxMemberCount = entity.maxMembersCount.orZero()
            ),
            formattedDate = entity.formatDate?.let(commonMapper::mapToGameDateUI),
            formatPrice = mapPrice(entity),
            priceAdditionalText = commonMapper.mapPriceAdditionalText(gameFormat),
            location = entity.location?.let { model ->
                commonMapper.mapLocationUI(model, gameFormat)
            },
            difficulty = "",
            status = entity.status?.let(commonMapper::mapToStatusUI),
            paymentMethod = entity.paymentMethod?.title.orEmpty(),
        )
    }

    private fun extractPackageNumber(gameName: String): String {
        val pattern = Regex("#(\\d+)")
        val match = pattern.find(gameName)

        return match?.value ?: ""
    }

    private fun mapPrice(entity: RudaGames): String {
        return buildString {
            entity.price?.let(::append)
            entity.currency?.let(::append)
        }
    }

    private fun uniqueGameName(gameName: String): String {
        val parts = gameName.split(" - ").map { it.trim() }

        if (parts.size < 3) return gameName

        val baseName = parts[0]

        return when (baseName) {
            MOZGOBOINYA -> {
                listOf(parts[0]) + parts.subList(2, parts.size)
            }

            TUTS_TUTS_QUIZ -> {
                parts.subList(1, parts.size)
            }

            else -> parts
        }.joinToString(" - ")
    }
}