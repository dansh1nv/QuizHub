package ru.dansh1nv.quiz.data.datasource.common

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quizapi.api.GeocodingService
import ru.dansh1nv.quizapi.model.base.GeoInfoDTO

class GeoInfoRemoteDataSource(
    private val api: GeocodingService
) {
    fun getGeoInfoByQuery(query: String): Flow<GeoInfoDTO> = api.getGeoInfo(query = query)

    fun getGeoInfoByCoordinates(latitude: Double, longitude: Double) = api.getGeoInfoByCoordinates(latitude, longitude)
}