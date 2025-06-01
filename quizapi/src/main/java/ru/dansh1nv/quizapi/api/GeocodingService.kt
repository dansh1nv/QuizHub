package ru.dansh1nv.quizapi.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.dansh1nv.quizapi.model.base.GeoCityDTO

/**
 * Ссылка на используемое Api - https://nominatim.org/release-docs/develop/api/Overview/
 * */
class GeocodingService(
    private val client: HttpClient
) {
    //TODO:Добавить обработку ошибок в сетевых запросах
    fun getCity(cityName: String): Flow<GeoCityDTO> = flow {
        val response = client.get {
            url {
                path(SEARCH_PATH)
                parameters.apply {
                    append(FORMAT, JSON_PARAM)
                    append(CITY, cityName)
                    append(LIMIT, "1")
                    append(ADDRESS_DETAILS, "1")
                }
            }
        }.body<List<GeoCityDTO>>()
        emit(response.first())
    }.flowOn(Dispatchers.IO)

    fun getCityByCoordinates(latitude: Double, longitude: Double): Flow<GeoCityDTO> = flow {
        val response = client.get {
            url {
                path(REVERSE_PATH)
                parameters.apply {
                    append(FORMAT, JSON_PARAM)
                    append(LATITUDE, latitude.toString())
                    append(LONGITUDE, longitude.toString())
                    append(ADDRESS_DETAILS, "1")
                    append(ZOOM, CITY_LEVEL_VALUE)
                }
            }
        }.body<GeoCityDTO>()
        emit(response)
    }.flowOn(Dispatchers.IO)

    private companion object {
        const val SEARCH_PATH = "search"
        const val REVERSE_PATH = "reverse"
        const val FORMAT = "format"
        const val CITY = "q"
        const val JSON_PARAM = "json"
        const val LIMIT = "limit"
        const val ADDRESS_DETAILS = "addressdetails"
        const val ZOOM = "zoom"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lon"
        const val CITY_LEVEL_VALUE = "10"
    }
}