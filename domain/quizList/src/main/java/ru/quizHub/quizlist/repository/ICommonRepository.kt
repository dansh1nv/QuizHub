package ru.quizHub.quizlist.repository

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.models.common.GeoInfo

interface ICommonRepository {
    fun getCurrentCity(): Flow<String>
    suspend fun updateCurrentCity(city: String)

    fun getGeoInfoByQuery(query: String): Flow<GeoInfo>
    fun getGeoInfoByCoordinates(latitude: Double, longitude: Double): Flow<GeoInfo>

    fun fetchCities(): Flow<List<City>>
}