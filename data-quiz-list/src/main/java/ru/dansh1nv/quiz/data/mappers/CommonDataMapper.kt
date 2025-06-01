package ru.dansh1nv.quiz.data.mappers

import ru.dansh1nv.quiz_list_domain.models.common.GeoCity
import ru.dansh1nv.quizapi.model.base.GeoCityDTO

class CommonDataMapper {

    fun mapGeoCity(city: GeoCityDTO): GeoCity {
        return GeoCity(
            name = city.name,
            latitude = city.latitude,
            longitude = city.longitude
        )
    }

}