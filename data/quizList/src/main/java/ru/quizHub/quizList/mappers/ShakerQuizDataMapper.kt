package ru.quizHub.quizList.mappers

import kotlinx.datetime.LocalDateTime
import ru.quizHub.common.StringDividerType
import ru.quizHub.common.formatStringsWithDividerPoints
import ru.quizHub.quizApi.model.shakerquiz.LocationDTO
import ru.quizHub.quizApi.model.shakerquiz.ShakerQuizItemDTO
import ru.quizHub.quizList.utils.MonthConverter
import ru.quizHub.quizlist.models.ShakerQuiz
import ru.quizHub.quizlist.models.Status
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.Location

class ShakerQuizDataMapper {

    fun mapToShakerQuiz(dtos: List<ShakerQuizItemDTO>): List<ShakerQuiz> {
        return dtos.map { dto -> map(dto) }
    }

    fun map(dto: ShakerQuizItemDTO): ShakerQuiz {
        return ShakerQuiz(
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