package ru.quizHub.quizList.mappers

import kotlinx.datetime.LocalDateTime
import ru.quizHub.quizApi.model.wowquiz.WowBarDTO
import ru.quizHub.quizApi.model.wowquiz.WowGameDTO
import ru.quizHub.quizList.utils.MonthConverter
import ru.quizHub.quizlist.models.Status
import ru.quizHub.quizlist.models.WowQuiz
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.Location

class WowQuizDataMapper {

    fun mapToWowQuiz(dtos: List<WowGameDTO>): List<WowQuiz> = dtos.map(::map)

    fun map(dto: WowGameDTO) = WowQuiz(
        id = dto.id?.toString(),
        title = dto.title,
        theme = dto.theme,
        template = dto.template,
        description = dto.description,
        shortDescription = dto.shortDescription,
        image = dto.imageUrl?.let(::mapImageUrl),
        price = dto.price,
        currency = dto.currency,
        eventTime = dto.date?.let(::mapToGameDate),
        formatTime = dto.date?.substringAfter(" ", missingDelimiterValue = ""),
        location = dto.bar?.let(::mapBar),
        status = mapRegistrationStatus(dto.registrationType),
        registrationType = dto.registrationType,
    )

    private fun mapImageUrl(path: String): String {
        if (path.startsWith("http", ignoreCase = true)) return path
        val suffix = path.trimStart('/')
        return "$BASE_CDN_URL/$suffix"
    }

    private fun mapBar(bar: WowBarDTO): Location {
        return Location(
            name = bar.title,
            latitude = bar.latitude,
            longitude = bar.longitude,
            address = bar.address,
            city = null,
        )
    }

    private fun mapToGameDate(raw: String): GameDate? {
        return try {
            val normalized = raw.replace(' ', 'T')
            val dateTime = LocalDateTime.parse(normalized)
            GameDate(
                dateTime = dateTime,
                day = dateTime.date.day.toString(),
                month = MonthConverter.getMonthNameByNumber(dateTime.month.ordinal + 1),
                time = dateTime.time.toString().take(5),
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun mapRegistrationStatus(registrationType: String?): Status? {
        return when (registrationType?.lowercase()) {
            "open" -> Status.WRITE_TO_GAME
            "reserve" -> Status.WRITE_TO_RESERVE
            else -> null
        }
    }

    companion object {
        private const val BASE_CDN_URL = "https://api.etowow.ru"
    }
}
