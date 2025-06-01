package ru.dansh1nv.quiz.data.datasource.common

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quizapi.api.GeocodingService
import ru.dansh1nv.quizapi.model.base.GeoCityDTO

class CityRemoteDataSource(
    private val api: GeocodingService
) {
    fun getCityInfoByName(name: String): Flow<GeoCityDTO> = api.getCity(cityName = name)

    fun getCityByCoordinates(latitude: Double, longitude: Double) = api.getCityByCoordinates(latitude, longitude)
}