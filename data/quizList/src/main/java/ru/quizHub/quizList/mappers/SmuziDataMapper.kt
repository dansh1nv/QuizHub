package ru.quizHub.quizList.mappers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json
import ru.quizHub.quizApi.model.smuzi.SmuziCharacteristicDTO
import ru.quizHub.quizApi.model.smuzi.SmuziGalleryItemDTO
import ru.quizHub.quizApi.model.smuzi.SmuziJsonOptionDTO
import ru.quizHub.quizApi.model.smuzi.SmuziProductDTO
import ru.quizHub.quizList.utils.MonthConverter
import ru.quizHub.quizlist.models.Smuzi
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.Location
import java.time.OffsetDateTime

class SmuziDataMapper {

    private val json = Json { ignoreUnknownKeys = true }

    fun mapToSmuzi(dtos: List<SmuziProductDTO>): List<Smuzi> = dtos.mapNotNull(::map)

    fun map(dto: SmuziProductDTO): Smuzi? {
        val parsedTitle = parseTitle(dto.title) ?: return null
        return Smuzi(
            id = dto.id.toString(),
            title = parsedTitle.theme,
            description = mapDescription(dto.description?.ifBlank { null } ?: dto.text),
            image = mapImage(dto.gallery),
            price = dto.price?.substringBefore(".")?.toIntOrNull(),
            eventTime = mapGameDate(
                day = parsedTitle.day,
                month = parsedTitle.month,
                time = parsedTitle.time,
            ),
            location = Location(
                name = parsedTitle.venue.ifBlank { null }
                    ?: characteristicValue(dto.characteristics, PLACE_CHARACTERISTIC),
                city = null,
                address = mapAddress(dto.jsonOptions),
                latitude = null,
                longitude = null,
            ),
            gameTypeLabel = characteristicValue(dto.characteristics, GAME_TYPE_CHARACTERISTIC),
            url = dto.url,
        )
    }

    private fun mapDescription(raw: String?): String? {
        if (raw.isNullOrBlank()) return null
        return raw
            .replace(BR_TAG_REGEX, "\n")
            .trim()
            .ifBlank { null }
    }

    private fun mapImage(galleryRaw: String?): String? {
        if (galleryRaw.isNullOrBlank()) return null
        return try {
            json.decodeFromString<List<SmuziGalleryItemDTO>>(galleryRaw)
                .firstOrNull()
                ?.img
        } catch (_: Exception) {
            null
        }
    }

    private fun mapAddress(jsonOptionsRaw: String?): String? {
        if (jsonOptionsRaw.isNullOrBlank()) return null
        return try {
            json.decodeFromString<List<SmuziJsonOptionDTO>>(jsonOptionsRaw)
                .firstOrNull { it.title == PLACE_CHARACTERISTIC }
                ?.values
                ?.firstOrNull()
        } catch (_: Exception) {
            null
        }
    }

    private fun characteristicValue(
        characteristics: List<SmuziCharacteristicDTO>?,
        title: String,
    ): String? {
        return characteristics
            ?.firstOrNull { it.title.equals(title, ignoreCase = true) }
            ?.value
    }

    private fun parseTitle(title: String?): ParsedTitle? {
        if (title.isNullOrBlank()) return null
        val match = TITLE_REGEX.matchEntire(title.trim()) ?: return null
        return ParsedTitle(
            day = match.groupValues[1],
            month = match.groupValues[2],
            time = match.groupValues[4],
            venue = match.groupValues[5],
            theme = match.groupValues[6].trim(),
        )
    }

    private fun mapGameDate(day: String, month: String, time: String): GameDate {
        val timeArray = time.trim().split(":", limit = 2)
        val currentDate = OffsetDateTime.now()
        val quizMonth = MonthConverter.getMonthByName(month)
        val year = if (currentDate.monthValue == 12 && quizMonth == 1) {
            currentDate.year + 1
        } else {
            currentDate.year
        }
        val localDate = LocalDate(
            year = year,
            monthNumber = quizMonth,
            dayOfMonth = day.toInt(),
        )
        val localTime = LocalTime(
            hour = timeArray.getOrNull(0)?.toIntOrNull() ?: 0,
            minute = timeArray.getOrNull(1)?.toIntOrNull() ?: 0,
        )
        return GameDate(
            dateTime = LocalDateTime(date = localDate, time = localTime),
            day = day,
            month = month,
            time = time.trim(),
        )
    }

    private data class ParsedTitle(
        val day: String,
        val month: String,
        val time: String,
        val venue: String,
        val theme: String,
    )

    companion object {
        private const val PLACE_CHARACTERISTIC = "Место проведения"
        private const val GAME_TYPE_CHARACTERISTIC = "Тип игры"
        private val BR_TAG_REGEX = Regex("""<br\s*/?>""", RegexOption.IGNORE_CASE)
        private val TITLE_REGEX = Regex(
            """^(\d{1,2})\s+(\S+)\s+\(([^)]+)\)\s+(\d{1,2}:\d{2})\s+\(([^)]+)\)\s+(.+)$"""
        )
    }
}
