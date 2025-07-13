package ru.dansh1nv.quiz.data.mappers

import ru.dansh1nv.quiz.data.models.CityDTO
import ru.dansh1nv.quiz.data.models.TeamSizeDTO
import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.models.common.GeoInfo
import ru.dansh1nv.quiz_list_domain.models.common.ShakerTeamSize
import ru.dansh1nv.quizapi.model.base.GeoInfoDTO

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