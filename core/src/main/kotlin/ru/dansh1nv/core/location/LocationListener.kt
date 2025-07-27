package ru.dansh1nv.core.location

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class LocationListener {
    private val currentLocation = MutableSharedFlow<Coordinates>(extraBufferCapacity = 1)

    suspend fun updateLocation(location: Coordinates) {
        currentLocation.emit(location)
    }

    fun observeCurrentLocation(): Flow<Coordinates> {
        return currentLocation
    }
}