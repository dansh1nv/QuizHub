package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.common.GeoInfo

interface ICommonRepository {
    fun getCurrentCity(): Flow<String>
    suspend fun updateCurrentCity(city: String)

    fun getGeoInfoByQuery(query: String): Flow<GeoInfo>
    fun getGeoInfoByCoordinates(latitude: Double, longitude: Double): Flow<GeoInfo>
}