package ru.dansh1nv.quiz_list_domain.interactors

import ru.dansh1nv.quiz_list_domain.repository.ICommonRepository

class GeoInfoInteractor(
    private val repository: ICommonRepository,
) {
    fun getGeoInfoByQuery(query: String) = repository.getGeoInfoByQuery(query)

    fun getGeoInfoByCoordinates(
        latitude: Double,
        longitude: Double,
    ) = repository.getGeoInfoByCoordinates(latitude, longitude)
}