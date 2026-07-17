package ru.quizHub.quizList.mappers

import ru.quizHub.common.StringDividerType
import ru.quizHub.common.formatStringsWithDividerPoints
import ru.quizHub.quizList.models.TagModel
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizlist.models.Difficulty
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType

internal class QuizPleaseMapper(private val commonMapper: CommonMapper) {

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
            description = entity.description?.substringBefore(SUBSTRING_TEXT)?.trim().orEmpty(),
            image = entity.image.orEmpty(),
            difficulty = entity.difficulty?.let(::mapDifficulty).orEmpty(),
            location = entity.location?.let { model ->
                commonMapper.mapLocationUI(model, gameFormat)
            },
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

    private fun mapDifficulty(difficulty: Difficulty): String {
        return when (difficulty) {
            Difficulty.LIGHT -> "легкая"
            Difficulty.MEDIUM -> "нормальная"
            Difficulty.HARD -> "сложная"
        }
    }
}