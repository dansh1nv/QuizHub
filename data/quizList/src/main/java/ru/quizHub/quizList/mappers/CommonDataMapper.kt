package ru.quizHub.quizList.mappers

import ru.quizHub.quizApi.model.base.GeoInfoDTO
import ru.quizHub.quizList.models.CityDTO
import ru.quizHub.quizList.models.TeamSizeDTO
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.models.common.GeoInfo
import ru.quizHub.quizlist.models.common.ShakerTeamSize

class CommonDataMapper {

    fun mapGeoCity(city: GeoInfoDTO): GeoInfo {
        return GeoInfo(
            name = city.name,
            latitude = city.latitude,
            longitude = city.longitude
        )
    }

    fun mapToCity(entity: CityDTO): City {
        return City(
            id = entity.id,
            name = entity.name.orEmpty(),
            squizId = entity.squizId,
            quizPleaseId = entity.quizPleaseId,
            shakerQuizId = entity.shakerQuizId,
            countryCode = entity.country.orEmpty(),
            shakerTeamSize = entity.shakerTeamSize?.let(::mapShakerTeamSize)
        )
    }

    fun mapShakerTeamSize(teamSize: TeamSizeDTO): ShakerTeamSize {
        return ShakerTeamSize(
            maximum = teamSize.maximum,
            minimum = teamSize.minimum,
        )
    }
}