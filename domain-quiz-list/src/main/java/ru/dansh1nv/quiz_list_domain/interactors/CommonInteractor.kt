package ru.dansh1nv.quiz_list_domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.repository.ICommonRepository

class CommonInteractor(
    private val repository: ICommonRepository,
) {

    fun observeCurrentCity(): Flow<String> {
        return repository.getCurrentCity()
    }

    suspend fun updateCurrentCity(city: String) {
        repository.updateCurrentCity(city)
    }

    fun fetchCities(): Flow<List<City>> {
        return repository.fetchCities()
    }

}