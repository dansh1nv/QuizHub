package ru.dansh1nv.quiz.data.mappers

import ru.dansh1nv.quiz_list_domain.models.common.GeoInfo
import ru.dansh1nv.quizapi.model.base.GeoInfoDTO

class CommonDataMapper {

    fun mapGeoCity(city: GeoInfoDTO): GeoInfo {
        return GeoInfo(
            name = city.name,
            latitude = city.latitude,
            longitude = city.longitude
        )
    }

}