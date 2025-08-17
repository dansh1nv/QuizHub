package ru.quizHub.quizList.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

class CommonRepository(
    private val remoteDataSource: GeoInfoRemoteDataSource,
    private val appDataStore: AppDataStore,
    private val mapper: CommonDataMapper,
) : ICommonRepository {

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
            val url = URL(CITY_URL)
            val request = url.readText()
            val json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
            val cities = json.decodeFromString<CityResponse>(request)
            val city = cities.cities.map { city ->
                mapper.mapToCity(city)
            }
            emit(city)
        }
            .flowOn(Dispatchers.IO)
            .catch {
                Timber.e(it)
                emptyList<City>()
            }
    }

    companion object {
        private const val CITY_URL = "https://dansh1nv.github.io/QuizHub/cities.json"
    }

}