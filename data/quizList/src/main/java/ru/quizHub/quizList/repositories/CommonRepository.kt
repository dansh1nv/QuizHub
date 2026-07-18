package ru.quizHub.quizList.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import ru.quizHub.core.datastore.AppDataStore
import ru.quizHub.quizList.datasource.common.GeoInfoRemoteDataSource
import ru.quizHub.quizList.mappers.CommonDataMapper
import ru.quizHub.quizList.models.CityResponse
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.models.common.GeoInfo
import ru.quizHub.quizlist.repository.ICommonRepository
import timber.log.Timber
import java.net.URL
import java.util.concurrent.TimeUnit

class CommonRepository(
    private val remoteDataSource: GeoInfoRemoteDataSource,
    private val appDataStore: AppDataStore,
    private val mapper: CommonDataMapper,
) : ICommonRepository {

    private companion object {
        const val CITY_URL = "https://dansh1nv.github.io/QuizHub/cities.json"
        val CITIES_TTL_MS: Long = TimeUnit.DAYS.toMillis(7)
        val jsonParser = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    override fun getCurrentCity(): Flow<String> {
        return appDataStore.dataFlow.map { appPreferences ->
            appPreferences.currentCity
        }
    }

    override suspend fun updateCurrentCity(city: String) {
        appDataStore.update {
            copy(currentCity = city)
        }
    }

    override fun getGeoInfoByQuery(query: String): Flow<GeoInfo> =
        remoteDataSource.getGeoInfoByQuery(query = query).map(mapper::mapGeoCity)

    override fun getGeoInfoByCoordinates(latitude: Double, longitude: Double): Flow<GeoInfo> =
        remoteDataSource.getGeoInfoByCoordinates(latitude, longitude).map(mapper::mapGeoCity)

    override fun fetchCities(): Flow<List<City>> {
        return flow {
            val preferences = appDataStore.dataFlow.first()
            val cachedJson = preferences.citiesJson
            val cacheAgeMs = System.currentTimeMillis() - preferences.citiesCachedAt
            val hasFreshCache = cachedJson.isNotEmpty() && cacheAgeMs <= CITIES_TTL_MS

            if (hasFreshCache) {
                Timber.d("Loading cities from cache (age=${cacheAgeMs}ms)")
                emit(parseCities(cachedJson))
                return@flow
            }

            Timber.d("Fetching cities from remote URL: $CITY_URL")
            try {
                val request = URL(CITY_URL).readText()
                val cityList = parseCities(request)
                appDataStore.update {
                    copy(
                        citiesJson = request,
                        citiesCachedAt = System.currentTimeMillis(),
                    )
                }
                Timber.i("Successfully loaded ${cityList.size} cities from remote and cached")
                emit(cityList)
            } catch (e: Exception) {
                if (cachedJson.isNotEmpty()) {
                    Timber.w(e, "Failed to fetch cities from $CITY_URL. Falling back to cache.")
                    emit(parseCities(cachedJson))
                } else {
                    Timber.e(e, "Failed to fetch cities from $CITY_URL. Returning empty list as fallback.")
                    emit(emptyList())
                }
            }
        }
            .flowOn(Dispatchers.IO)
    }

    private fun parseCities(json: String): List<City> {
        val cities = jsonParser.decodeFromString<CityResponse>(json)
        return cities.cities.map(mapper::mapToCity)
    }
}
