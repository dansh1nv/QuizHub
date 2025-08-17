package ru.quizHub.quizlist.interactors

import kotlinx.coroutines.flow.Flow
import ru.quizHub.quizlist.models.common.City
import ru.quizHub.quizlist.repository.ICommonRepository

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