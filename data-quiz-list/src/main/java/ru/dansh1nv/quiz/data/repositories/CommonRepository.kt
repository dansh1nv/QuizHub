package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.core.datastore.AppDataStore
import ru.dansh1nv.core.datastore.AppPreferences
import ru.dansh1nv.quiz.data.datasource.common.GeoInfoRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.CommonDataMapper
import ru.dansh1nv.quiz_list_domain.models.common.GeoInfo
import ru.dansh1nv.quiz_list_domain.repository.ICommonRepository

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

}