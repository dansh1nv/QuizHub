package ru.dansh1nv.quiz.data.mappers

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import ru.dansh1nv.common.StringDividerType
import ru.dansh1nv.common.formatStringsWithDividerPoints
import ru.dansh1nv.quiz.data.utils.MonthConverter
import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.quiz_list_domain.models.Status
import ru.dansh1nv.quiz_list_domain.models.common.GameDate
import ru.dansh1nv.quiz_list_domain.models.common.Location
import ru.dansh1nv.quizapi.model.shakerquiz.LocationDTO
import ru.dansh1nv.quizapi.model.shakerquiz.ShakerQuizItemDTO
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ShakerQuizDataMapper {

    fun mapToShakerQuiz(dtos: List<ShakerQuizItemDTO>): List<ShakerQuiz> {
        return dtos.map { dto ->
            ShakerQuiz(
                id = dto.id,
                theme = dto.title,
                packageNumber = dto.number,
                description = dto.description,
                shortDescription = dto.shortDescription,
                status = mapStatus(dto.status),
                eventTime = mapToGameDate(dto.eventTime.orEmpty()),
                formatTime = dto.location?.gameTime,
                price = dto.price?.toInt(),
                currency = dto.currency,
                minMembersCount = dto.minMembersCount,
                maxMembersCount = dto.maxMembersCount,
                location = dto.location?.let(::mapToLocation),
                image = dto.image?.media?.cachedLink.orEmpty(),
                capacityStatus = dto.capacityStatus,
            )
        }
    }

    private fun mapStatus(statusText: String?): Status? {
        return Status.entries.firstOrNull { statusText == it.shakerId }
    }

    private fun mapToLocation(dto: LocationDTO): Location {
        return Location(
            name = dto.name,
            latitude = dto.latitude,
            longitude = dto.longitude,
            address = formatStringsWithDividerPoints(
                arrayOf(dto.street, dto.houseNumber),
                StringDividerType.CommaSpace
            ),
            city = dto.city?.name,
        )
    }

    private fun mapToGameDate(eventTime: String): GameDate {
        val date = LocalDateTime.parse(eventTime)
        return GameDate(
            dateTime = date,
            day = date.date.dayOfMonth.toString(),
            month = MonthConverter.getMonthNameByNumber(date.monthNumber),
            time = date.time.toString(),
        )
    }
}