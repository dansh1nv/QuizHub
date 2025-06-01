package ru.dansh1nv.quiz_list_domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.common.GeoCity

interface ICommonRepository {
    fun getGeoCityByName(cityName: String): Flow<GeoCity>
    fun getGeoCityByCoordinates(latitude: Double, longitude: Double): Flow<GeoCity>
}