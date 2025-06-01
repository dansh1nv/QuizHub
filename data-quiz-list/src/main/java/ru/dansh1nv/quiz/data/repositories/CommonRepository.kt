package ru.dansh1nv.quiz.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.dansh1nv.quiz.data.datasource.common.CityRemoteDataSource
import ru.dansh1nv.quiz.data.mappers.CommonDataMapper
import ru.dansh1nv.quiz_list_domain.models.common.GeoCity
import ru.dansh1nv.quiz_list_domain.repository.ICommonRepository

class CommonRepository(
    private val remoteDataSource: CityRemoteDataSource,
    private val mapper: CommonDataMapper,
) : ICommonRepository {

    override fun getGeoCityByName(cityName: String): Flow<GeoCity> =
        remoteDataSource.getCityInfoByName(name = cityName).map(mapper::mapGeoCity)

    override fun getGeoCityByCoordinates(latitude: Double, longitude: Double): Flow<GeoCity> =
        remoteDataSource.getCityByCoordinates(latitude, longitude).map(mapper::mapGeoCity)

}