package ru.dansh1nv.core.presentation

object PlaceTypeValidator {
    private const val ONLINE = "онлайн"

    fun isOnlineLocation(place: String): Boolean {
        return place.lowercase().contains(ONLINE)
    }
}