package ru.dansh1nv.quiz.data.mappers

import ru.dansh1nv.quiz_list_domain.models.ShakerQuiz
import ru.dansh1nv.quiz_list_domain.models.Status
import ru.dansh1nv.quiz_list_domain.models.common.Location
import ru.dansh1nv.quizapi.model.shakerquiz.LocationDTO
import ru.dansh1nv.quizapi.model.shakerquiz.ShakerQuizItemDTO
import java.math.BigDecimal

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
                eventTime = dto.eventTime,
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
            address = buildString {
                dto.street?.let(::append)
                append(", ")
                dto.houseNumber?.let(::append)
            },
            city = dto.city?.name,
        )
    }
}