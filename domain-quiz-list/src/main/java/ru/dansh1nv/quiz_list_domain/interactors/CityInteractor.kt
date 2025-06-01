package ru.dansh1nv.quiz_list_domain.interactors

import ru.dansh1nv.quiz_list_domain.repository.ICommonRepository

class CityInteractor(
    private val repository: ICommonRepository,
) {
    fun getGeoCityByName(cityName: String) = repository.getGeoCityByName(cityName)

    fun getGeoCityByCoordinates(
        latitude: Double,
        longitude: Double,
    ) = repository.getGeoCityByCoordinates(latitude, longitude)
}