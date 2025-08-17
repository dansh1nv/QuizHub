package ru.quizHub.quizList.datasource.common

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizApi.api.GeocodingService
import ru.quizHub.quizApi.model.base.GeoInfoDTO

class GeoInfoRemoteDataSource(
    private val api: GeocodingService
) {
    fun getGeoInfoByQuery(query: String): Flow<GeoInfoDTO> = api.getGeoInfo(query = query)

    fun getGeoInfoByCoordinates(latitude: Double, longitude: Double) = api.getGeoInfoByCoordinates(latitude, longitude)
}