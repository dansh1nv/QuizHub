package ru.quizHub.quizlist.interactors

import ru.quizHub.quizlist.repository.ICommonRepository

class GeoInfoInteractor(
    private val repository: ru.quizHub.quizlist.repository.ICommonRepository,
) {
    fun getGeoInfoByQuery(query: String) = repository.getGeoInfoByQuery(query)

    fun getGeoInfoByCoordinates(
        latitude: Double,
        longitude: Double,
    ) = repository.getGeoInfoByCoordinates(latitude, longitude)
}