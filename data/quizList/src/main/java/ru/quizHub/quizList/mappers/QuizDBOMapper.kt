package ru.quizHub.quizList.mappers

import kotlinx.datetime.LocalDateTime
import ru.quizHub.database.models.QuizDBO
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.RudaGames
import ru.quizHub.quizlist.models.SQuiz
import ru.quizHub.quizlist.models.ShakerQuiz
import ru.quizHub.quizlist.models.Smuzi
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType
import ru.quizHub.quizlist.models.common.Location

class QuizDBOMapper {

    private companion object {
        const val ORG_QUIZ_PLEASE = "QuizPlease"
        const val ORG_SQUIZ = "Squiz"
        const val ORG_SHAKER = "ShakerQuiz"
        const val ORG_RUDA = "RudaGames"
        const val ORG_WOW = "WowQuiz"
        const val ORG_SMUZI = "Smuzi"
        const val SEPARATOR = "|"
    }

    fun mapToQuiz(dbo: QuizDBO): Quiz {
        val location = Location(
            name = dbo.place.ifEmpty { null },
            city = dbo.city.ifEmpty { null },
            address = dbo.address.ifEmpty { null },
            latitude = null,
            longitude = null,
        )
        val gameDate = parseGameDate(dbo.date, dbo.time)

        return when (dbo.organization) {
            ORG_SQUIZ -> SQuiz(
                id = dbo.id.toString(),
                gameDate = gameDate,
                type = GameType.entries.firstOrNull { it.name == dbo.type },
                format = GameFormat.entries.firstOrNull { it.name == dbo.format },
                theme = dbo.theme.ifEmpty { null },
                packageNumber = dbo.packageNumber.ifEmpty { null },
                description = dbo.description.ifEmpty { null },
                additionDescription = null,
                image = dbo.image.takeIf { it.isNotEmpty() },
                price = dbo.price.ifEmpty { null },
                location = location,
                status = null,
                difficult = null,
            )

            ORG_SHAKER -> ShakerQuiz(
                id = dbo.id.toString(),
                theme = dbo.theme.ifEmpty { null },
                packageNumber = dbo.packageNumber.ifEmpty { null },
                description = dbo.description.ifEmpty { null },
                shortDescription = null,
                status = null,
                eventTime = gameDate,
                formatTime = dbo.time.ifEmpty { null },
                price = dbo.price.toIntOrNull(),
                currency = null,
                minMembersCount = null,
                maxMembersCount = null,
                location = location,
                image = dbo.image.takeIf { it.isNotEmpty() },
                capacityStatus = null,
            )

            ORG_RUDA -> RudaGames(
                id = dbo.id.toString(),
                title = dbo.name.ifEmpty { null },
                description = dbo.description.ifEmpty { null },
                image = dbo.image.takeIf { it.isNotEmpty() },
                gameType = GameType.entries.firstOrNull { it.name == dbo.type },
                formatDate = gameDate,
                formatTime = dbo.time.ifEmpty { null },
                price = dbo.price.toIntOrNull(),
                currency = null,
                paymentMethod = null,
                location = location,
                status = null,
                minMembersCount = null,
                maxMembersCount = null,
            )

            ORG_WOW -> WowQuiz(
                id = dbo.id.toString(),
                title = dbo.name.ifEmpty { null },
                theme = dbo.theme.ifEmpty { null },
                template = null,
                description = dbo.description.ifEmpty { null },
                shortDescription = null,
                image = dbo.image.takeIf { it.isNotEmpty() },
                price = dbo.price.toIntOrNull(),
                currency = null,
                eventTime = gameDate,
                formatTime = dbo.time.ifEmpty { null },
                location = location,
                status = null,
                registrationType = null,
            )

            ORG_SMUZI -> Smuzi(
                id = dbo.id.toString(),
                title = dbo.theme.ifEmpty { null },
                description = dbo.description.ifEmpty { null },
                image = dbo.image.takeIf { it.isNotEmpty() },
                price = dbo.price.toIntOrNull(),
                eventTime = gameDate,
                location = location,
                gameTypeLabel = dbo.type.ifEmpty { null },
                url = null,
            )

            else -> QuizPlease(
                id = dbo.id.toString(),
                title = dbo.name.ifEmpty { null },
                packageNumber = dbo.packageNumber.ifEmpty { null },
                description = dbo.description.ifEmpty { null },
                image = dbo.image.takeIf { it.isNotEmpty() },
                gameFormat = GameFormat.entries.firstOrNull { it.name == dbo.format },
                datetime = null,
                formatDate = gameDate,
                price = dbo.price.toIntOrNull(),
                formatPrice = dbo.price.ifEmpty { null },
                location = location,
                difficulty = null,
                status = null,
                paymentMethod = null,
            )
        }
    }

    fun mapToQuizDBO(quiz: Quiz, cityName: String): QuizDBO {
        return when (quiz) {
            is QuizPlease -> mapQuizPleaseToDBO(quiz, cityName)
            is SQuiz -> mapSQuizToDBO(quiz, cityName)
            is ShakerQuiz -> mapShakerToDBO(quiz, cityName)
            is RudaGames -> mapRudaToDBO(quiz, cityName)
            is WowQuiz -> mapWowToDBO(quiz, cityName)
            is Smuzi -> mapSmuziToDBO(quiz, cityName)
        }
    }

    fun mapToQuizList(dboList: List<QuizDBO>): List<Quiz> = dboList.map(::mapToQuiz)

    fun mapToQuizDBOList(quizList: List<Quiz>, cityName: String): List<QuizDBO> =
        quizList.map { mapToQuizDBO(it, cityName) }

    private fun mapQuizPleaseToDBO(quiz: QuizPlease, cityName: String) = QuizDBO(
        id = 0,
        organization = ORG_QUIZ_PLEASE,
        city = quiz.location?.city ?: cityName,
        date = serializeGameDate(quiz.formatDate),
        format = quiz.gameFormat?.name.orEmpty(),
        type = "",
        theme = quiz.title.orEmpty(),
        packageNumber = quiz.packageNumber.orEmpty(),
        name = quiz.title ?: "Без названия",
        description = quiz.description.orEmpty(),
        place = quiz.location?.name.orEmpty(),
        time = quiz.formatDate?.time.orEmpty(),
        address = quiz.location?.address.orEmpty(),
        price = quiz.price?.toString().orEmpty(),
        image = quiz.image.orEmpty(),
    )

    private fun mapSQuizToDBO(quiz: SQuiz, cityName: String) = QuizDBO(
        id = 0,
        organization = ORG_SQUIZ,
        city = quiz.location?.city ?: cityName,
        date = serializeGameDate(quiz.gameDate),
        format = quiz.format?.name.orEmpty(),
        type = quiz.type?.name.orEmpty(),
        theme = quiz.theme.orEmpty(),
        packageNumber = quiz.packageNumber.orEmpty(),
        name = quiz.theme ?: "Без названия",
        description = quiz.description.orEmpty(),
        place = quiz.location?.name.orEmpty(),
        time = quiz.gameDate?.time.orEmpty(),
        address = quiz.location?.address.orEmpty(),
        price = quiz.price.orEmpty(),
        image = quiz.image.orEmpty(),
    )

    private fun mapShakerToDBO(quiz: ShakerQuiz, cityName: String) = QuizDBO(
        id = 0,
        organization = ORG_SHAKER,
        city = quiz.location?.city ?: cityName,
        date = serializeGameDate(quiz.eventTime),
        format = "",
        type = "",
        theme = quiz.theme.orEmpty(),
        packageNumber = quiz.packageNumber.orEmpty(),
        name = quiz.theme ?: "Без названия",
        description = quiz.description.orEmpty(),
        place = quiz.location?.name.orEmpty(),
        time = quiz.eventTime?.time.orEmpty(),
        address = quiz.location?.address.orEmpty(),
        price = quiz.price?.toString().orEmpty(),
        image = quiz.image.orEmpty(),
    )

    private fun mapRudaToDBO(quiz: RudaGames, cityName: String) = QuizDBO(
        id = 0,
        organization = ORG_RUDA,
        city = quiz.location?.city ?: cityName,
        date = serializeGameDate(quiz.formatDate),
        format = "",
        type = quiz.gameType?.name.orEmpty(),
        theme = quiz.title.orEmpty(),
        packageNumber = "",
        name = quiz.title ?: "Без названия",
        description = quiz.description.orEmpty(),
        place = quiz.location?.name.orEmpty(),
        time = quiz.formatDate?.time.orEmpty(),
        address = quiz.location?.address.orEmpty(),
        price = quiz.price?.toString().orEmpty(),
        image = quiz.image.orEmpty(),
    )

    private fun mapWowToDBO(quiz: WowQuiz, cityName: String) = QuizDBO(
        id = 0,
        organization = ORG_WOW,
        city = quiz.location?.city ?: cityName,
        date = serializeGameDate(quiz.eventTime),
        format = "",
        type = "",
        theme = quiz.theme.orEmpty(),
        packageNumber = "",
        name = quiz.title ?: quiz.theme ?: "Без названия",
        description = quiz.description.orEmpty(),
        place = quiz.location?.name.orEmpty(),
        time = quiz.eventTime?.time.orEmpty(),
        address = quiz.location?.address.orEmpty(),
        price = quiz.price?.toString().orEmpty(),
        image = quiz.image.orEmpty(),
    )

    private fun mapSmuziToDBO(quiz: Smuzi, cityName: String) = QuizDBO(
        id = 0,
        organization = ORG_SMUZI,
        city = quiz.location?.city ?: cityName,
        date = serializeGameDate(quiz.eventTime),
        format = "",
        type = quiz.gameTypeLabel.orEmpty(),
        theme = quiz.title.orEmpty(),
        packageNumber = "",
        name = quiz.title ?: "Без названия",
        description = quiz.description.orEmpty(),
        place = quiz.location?.name.orEmpty(),
        time = quiz.eventTime?.time.orEmpty(),
        address = quiz.location?.address.orEmpty(),
        price = quiz.price?.toString().orEmpty(),
        image = quiz.image.orEmpty(),
    )

    private fun serializeGameDate(gameDate: GameDate?): String {
        if (gameDate == null) return ""
        return "${gameDate.dateTime}$SEPARATOR${gameDate.day}$SEPARATOR${gameDate.month}$SEPARATOR${gameDate.time}"
    }

    private fun parseGameDate(dateStr: String, timeStr: String): GameDate? {
        if (dateStr.isEmpty()) return null
        val parts = dateStr.split(SEPARATOR)
        if (parts.size < 4) return null
        return try {
            val dateTime = LocalDateTime.parse(parts[0])
            GameDate(
                dateTime = dateTime,
                day = parts[1],
                month = parts[2],
                time = parts[3].ifEmpty { timeStr },
            )
        } catch (e: Exception) {
            null
        }
    }
}
