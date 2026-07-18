package ru.quizHub.quizList.mappers

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.quizHub.database.models.QuizDBO
import ru.quizHub.quizList.cache.CachedGameDate
import ru.quizHub.quizList.cache.CachedLocation
import ru.quizHub.quizList.cache.CachedQuizPayload
import ru.quizHub.quizList.cache.QuizOrganization
import ru.quizHub.quizlist.models.Difficulty
import ru.quizHub.quizlist.models.Quiz
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.RudaGames
import ru.quizHub.quizlist.models.SQuiz
import ru.quizHub.quizlist.models.ShakerQuiz
import ru.quizHub.quizlist.models.Smuzi
import ru.quizHub.quizlist.models.Status
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.GameType
import ru.quizHub.quizlist.models.common.Location
import ru.quizHub.quizlist.models.common.PaymentMethod
import timber.log.Timber

class QuizDBOMapper {

    private companion object {
        const val SEPARATOR = "|"
        val payloadJson = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            classDiscriminator = "type"
        }
    }

    fun mapToQuiz(dbo: QuizDBO): Quiz {
        if (dbo.payload.isNotEmpty()) {
            try {
                return payloadToQuiz(payloadJson.decodeFromString<CachedQuizPayload>(dbo.payload))
            } catch (e: Exception) {
                Timber.w(e, "Failed to decode quiz payload for id=${dbo.id}, falling back to flat columns")
            }
        }
        return mapFromFlatColumns(dbo)
    }

    fun mapToQuizDBO(quiz: Quiz, cityName: String, cachedAt: Long = System.currentTimeMillis()): QuizDBO {
        val payload = quizToPayload(quiz)
        val organization = organizationOf(quiz)
        val remoteId = quizRemoteId(quiz)
        val location = locationOf(quiz)
        val gameDate = gameDateOf(quiz)

        return QuizDBO(
            id = cacheKey(organization, remoteId),
            organization = organization,
            city = location?.city ?: cityName,
            date = serializeGameDate(gameDate),
            format = formatOf(quiz),
            type = typeOf(quiz),
            theme = themeOf(quiz),
            packageNumber = packageNumberOf(quiz),
            name = nameOf(quiz),
            description = descriptionOf(quiz),
            place = location?.name.orEmpty(),
            time = gameDate?.time.orEmpty(),
            address = location?.address.orEmpty(),
            price = priceOf(quiz),
            image = imageOf(quiz),
            cachedAt = cachedAt,
            payload = payloadJson.encodeToString(payload),
        )
    }

    fun mapToQuizList(dboList: List<QuizDBO>): List<Quiz> = dboList.map(::mapToQuiz)

    fun mapToQuizDBOList(
        quizList: List<Quiz>,
        cityName: String,
        cachedAt: Long = System.currentTimeMillis(),
    ): List<QuizDBO> = quizList.map { mapToQuizDBO(it, cityName, cachedAt) }

    private fun quizToPayload(quiz: Quiz): CachedQuizPayload = when (quiz) {
        is QuizPlease -> CachedQuizPayload.QuizPlease(
            id = quiz.id,
            title = quiz.title,
            packageNumber = quiz.packageNumber,
            description = quiz.description,
            image = quiz.image,
            gameFormat = quiz.gameFormat?.name,
            datetime = quiz.datetime,
            formatDate = quiz.formatDate?.toCached(),
            price = quiz.price,
            formatPrice = quiz.formatPrice,
            location = quiz.location?.toCached(),
            difficulty = quiz.difficulty?.name,
            status = quiz.status?.name,
            paymentMethod = quiz.paymentMethod?.name,
        )

        is SQuiz -> CachedQuizPayload.Squiz(
            id = quiz.id,
            gameDate = quiz.gameDate?.toCached(),
            type = quiz.type?.name,
            format = quiz.format?.name,
            theme = quiz.theme,
            packageNumber = quiz.packageNumber,
            description = quiz.description,
            additionDescription = quiz.additionDescription,
            image = quiz.image,
            price = quiz.price,
            location = quiz.location?.toCached(),
            status = quiz.status?.name,
            difficult = quiz.difficult,
        )

        is ShakerQuiz -> CachedQuizPayload.Shaker(
            id = quiz.id,
            theme = quiz.theme,
            packageNumber = quiz.packageNumber,
            description = quiz.description,
            shortDescription = quiz.shortDescription,
            status = quiz.status?.name,
            eventTime = quiz.eventTime?.toCached(),
            formatTime = quiz.formatTime,
            price = quiz.price,
            currency = quiz.currency,
            minMembersCount = quiz.minMembersCount,
            maxMembersCount = quiz.maxMembersCount,
            location = quiz.location?.toCached(),
            image = quiz.image,
            capacityStatus = quiz.capacityStatus,
        )

        is RudaGames -> CachedQuizPayload.Ruda(
            id = quiz.id,
            title = quiz.title,
            description = quiz.description,
            image = quiz.image,
            gameType = quiz.gameType?.name,
            formatDate = quiz.formatDate?.toCached(),
            formatTime = quiz.formatTime,
            price = quiz.price,
            currency = quiz.currency,
            paymentMethod = quiz.paymentMethod?.name,
            location = quiz.location?.toCached(),
            status = quiz.status?.name,
            minMembersCount = quiz.minMembersCount,
            maxMembersCount = quiz.maxMembersCount,
        )

        is WowQuiz -> CachedQuizPayload.Wow(
            id = quiz.id,
            title = quiz.title,
            theme = quiz.theme,
            template = quiz.template,
            description = quiz.description,
            shortDescription = quiz.shortDescription,
            image = quiz.image,
            price = quiz.price,
            currency = quiz.currency,
            eventTime = quiz.eventTime?.toCached(),
            formatTime = quiz.formatTime,
            location = quiz.location?.toCached(),
            status = quiz.status?.name,
            registrationType = quiz.registrationType,
        )

        is Smuzi -> CachedQuizPayload.Smuzi(
            id = quiz.id,
            title = quiz.title,
            description = quiz.description,
            image = quiz.image,
            price = quiz.price,
            eventTime = quiz.eventTime?.toCached(),
            location = quiz.location?.toCached(),
            gameTypeLabel = quiz.gameTypeLabel,
            url = quiz.url,
        )
    }

    private fun payloadToQuiz(payload: CachedQuizPayload): Quiz = when (payload) {
        is CachedQuizPayload.QuizPlease -> QuizPlease(
            id = payload.id,
            title = payload.title,
            packageNumber = payload.packageNumber,
            description = payload.description,
            image = payload.image,
            gameFormat = payload.gameFormat.toEnumOrNull<GameFormat>(),
            datetime = payload.datetime,
            formatDate = payload.formatDate?.toDomain(),
            price = payload.price,
            formatPrice = payload.formatPrice,
            location = payload.location?.toDomain(),
            difficulty = payload.difficulty.toEnumOrNull<Difficulty>(),
            status = payload.status.toEnumOrNull<Status>(),
            paymentMethod = payload.paymentMethod.toEnumOrNull<PaymentMethod>(),
        )

        is CachedQuizPayload.Squiz -> SQuiz(
            id = payload.id,
            gameDate = payload.gameDate?.toDomain(),
            type = payload.type.toEnumOrNull<GameType>(),
            format = payload.format.toEnumOrNull<GameFormat>(),
            theme = payload.theme,
            packageNumber = payload.packageNumber,
            description = payload.description,
            additionDescription = payload.additionDescription,
            image = payload.image,
            price = payload.price,
            location = payload.location?.toDomain(),
            status = payload.status.toEnumOrNull<Status>(),
            difficult = payload.difficult,
        )

        is CachedQuizPayload.Shaker -> ShakerQuiz(
            id = payload.id,
            theme = payload.theme,
            packageNumber = payload.packageNumber,
            description = payload.description,
            shortDescription = payload.shortDescription,
            status = payload.status.toEnumOrNull<Status>(),
            eventTime = payload.eventTime?.toDomain(),
            formatTime = payload.formatTime,
            price = payload.price,
            currency = payload.currency,
            minMembersCount = payload.minMembersCount,
            maxMembersCount = payload.maxMembersCount,
            location = payload.location?.toDomain(),
            image = payload.image,
            capacityStatus = payload.capacityStatus,
        )

        is CachedQuizPayload.Ruda -> RudaGames(
            id = payload.id,
            title = payload.title,
            description = payload.description,
            image = payload.image,
            gameType = payload.gameType.toEnumOrNull<GameType>(),
            formatDate = payload.formatDate?.toDomain(),
            formatTime = payload.formatTime,
            price = payload.price,
            currency = payload.currency,
            paymentMethod = payload.paymentMethod.toEnumOrNull<PaymentMethod>(),
            location = payload.location?.toDomain(),
            status = payload.status.toEnumOrNull<Status>(),
            minMembersCount = payload.minMembersCount,
            maxMembersCount = payload.maxMembersCount,
        )

        is CachedQuizPayload.Wow -> WowQuiz(
            id = payload.id,
            title = payload.title,
            theme = payload.theme,
            template = payload.template,
            description = payload.description,
            shortDescription = payload.shortDescription,
            image = payload.image,
            price = payload.price,
            currency = payload.currency,
            eventTime = payload.eventTime?.toDomain(),
            formatTime = payload.formatTime,
            location = payload.location?.toDomain(),
            status = payload.status.toEnumOrNull<Status>(),
            registrationType = payload.registrationType,
        )

        is CachedQuizPayload.Smuzi -> Smuzi(
            id = payload.id,
            title = payload.title,
            description = payload.description,
            image = payload.image,
            price = payload.price,
            eventTime = payload.eventTime?.toDomain(),
            location = payload.location?.toDomain(),
            gameTypeLabel = payload.gameTypeLabel,
            url = payload.url,
        )
    }

    private fun mapFromFlatColumns(dbo: QuizDBO): Quiz {
        val location = Location(
            name = dbo.place.ifEmpty { null },
            city = dbo.city.ifEmpty { null },
            address = dbo.address.ifEmpty { null },
            latitude = null,
            longitude = null,
        )
        val gameDate = parseGameDate(dbo.date, dbo.time)
        val quizId = remoteIdFromCacheKey(dbo.id)

        return when (dbo.organization) {
            QuizOrganization.SQUIZ -> SQuiz(
                id = quizId,
                gameDate = gameDate,
                type = dbo.type.toEnumOrNull<GameType>(),
                format = dbo.format.toEnumOrNull<GameFormat>(),
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

            QuizOrganization.SHAKER -> ShakerQuiz(
                id = quizId,
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

            QuizOrganization.RUDA -> RudaGames(
                id = quizId,
                title = dbo.name.ifEmpty { null },
                description = dbo.description.ifEmpty { null },
                image = dbo.image.takeIf { it.isNotEmpty() },
                gameType = dbo.type.toEnumOrNull<GameType>(),
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

            QuizOrganization.WOW -> WowQuiz(
                id = quizId,
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

            QuizOrganization.SMUZI -> Smuzi(
                id = quizId,
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
                id = quizId,
                title = dbo.name.ifEmpty { null },
                packageNumber = dbo.packageNumber.ifEmpty { null },
                description = dbo.description.ifEmpty { null },
                image = dbo.image.takeIf { it.isNotEmpty() },
                gameFormat = dbo.format.toEnumOrNull<GameFormat>(),
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

    private fun organizationOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> QuizOrganization.QUIZ_PLEASE
        is SQuiz -> QuizOrganization.SQUIZ
        is ShakerQuiz -> QuizOrganization.SHAKER
        is RudaGames -> QuizOrganization.RUDA
        is WowQuiz -> QuizOrganization.WOW
        is Smuzi -> QuizOrganization.SMUZI
    }

    private fun quizRemoteId(quiz: Quiz): String? = when (quiz) {
        is QuizPlease -> quiz.id
        is SQuiz -> quiz.id
        is ShakerQuiz -> quiz.id
        is RudaGames -> quiz.id
        is WowQuiz -> quiz.id
        is Smuzi -> quiz.id
    }

    private fun locationOf(quiz: Quiz): Location? = when (quiz) {
        is QuizPlease -> quiz.location
        is SQuiz -> quiz.location
        is ShakerQuiz -> quiz.location
        is RudaGames -> quiz.location
        is WowQuiz -> quiz.location
        is Smuzi -> quiz.location
    }

    private fun gameDateOf(quiz: Quiz): GameDate? = when (quiz) {
        is QuizPlease -> quiz.formatDate
        is SQuiz -> quiz.gameDate
        is ShakerQuiz -> quiz.eventTime
        is RudaGames -> quiz.formatDate
        is WowQuiz -> quiz.eventTime
        is Smuzi -> quiz.eventTime
    }

    private fun formatOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> quiz.gameFormat?.name.orEmpty()
        is SQuiz -> quiz.format?.name.orEmpty()
        else -> ""
    }

    private fun typeOf(quiz: Quiz): String = when (quiz) {
        is SQuiz -> quiz.type?.name.orEmpty()
        is RudaGames -> quiz.gameType?.name.orEmpty()
        is Smuzi -> quiz.gameTypeLabel.orEmpty()
        else -> ""
    }

    private fun themeOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> quiz.title.orEmpty()
        is SQuiz -> quiz.theme.orEmpty()
        is ShakerQuiz -> quiz.theme.orEmpty()
        is RudaGames -> quiz.title.orEmpty()
        is WowQuiz -> quiz.theme.orEmpty()
        is Smuzi -> quiz.title.orEmpty()
    }

    private fun packageNumberOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> quiz.packageNumber.orEmpty()
        is SQuiz -> quiz.packageNumber.orEmpty()
        is ShakerQuiz -> quiz.packageNumber.orEmpty()
        else -> ""
    }

    private fun nameOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> quiz.title ?: "Без названия"
        is SQuiz -> quiz.theme ?: "Без названия"
        is ShakerQuiz -> quiz.theme ?: "Без названия"
        is RudaGames -> quiz.title ?: "Без названия"
        is WowQuiz -> quiz.title ?: quiz.theme ?: "Без названия"
        is Smuzi -> quiz.title ?: "Без названия"
    }

    private fun descriptionOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> quiz.description.orEmpty()
        is SQuiz -> quiz.description.orEmpty()
        is ShakerQuiz -> quiz.description.orEmpty()
        is RudaGames -> quiz.description.orEmpty()
        is WowQuiz -> quiz.description.orEmpty()
        is Smuzi -> quiz.description.orEmpty()
    }

    private fun priceOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> quiz.price?.toString().orEmpty()
        is SQuiz -> quiz.price.orEmpty()
        is ShakerQuiz -> quiz.price?.toString().orEmpty()
        is RudaGames -> quiz.price?.toString().orEmpty()
        is WowQuiz -> quiz.price?.toString().orEmpty()
        is Smuzi -> quiz.price?.toString().orEmpty()
    }

    private fun imageOf(quiz: Quiz): String = when (quiz) {
        is QuizPlease -> quiz.image.orEmpty()
        is SQuiz -> quiz.image.orEmpty()
        is ShakerQuiz -> quiz.image.orEmpty()
        is RudaGames -> quiz.image.orEmpty()
        is WowQuiz -> quiz.image.orEmpty()
        is Smuzi -> quiz.image.orEmpty()
    }

    private fun cacheKey(organization: String, remoteId: String?): String =
        "$organization$SEPARATOR${remoteId.orEmpty()}"

    private fun remoteIdFromCacheKey(cacheKey: String): String {
        val separatorIndex = cacheKey.indexOf(SEPARATOR)
        return if (separatorIndex >= 0) {
            cacheKey.substring(separatorIndex + 1)
        } else {
            cacheKey
        }
    }

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

    private fun GameDate.toCached() = CachedGameDate(
        dateTime = dateTime.toString(),
        day = day,
        month = month,
        time = time,
    )

    private fun CachedGameDate.toDomain(): GameDate? = try {
        GameDate(
            dateTime = LocalDateTime.parse(dateTime),
            day = day,
            month = month,
            time = time,
        )
    } catch (e: Exception) {
        null
    }

    private fun Location.toCached() = CachedLocation(
        name = name,
        latitude = latitude,
        longitude = longitude,
        address = address,
        city = city,
    )

    private fun CachedLocation.toDomain() = Location(
        name = name,
        latitude = latitude,
        longitude = longitude,
        address = address,
        city = city,
    )

    private inline fun <reified T : Enum<T>> String?.toEnumOrNull(): T? =
        this?.let { runCatching { enumValueOf<T>(it) }.getOrNull() }
}
