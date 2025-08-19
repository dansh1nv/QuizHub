package ru.quizHub.quizList.models.item

data class LocationUI(
    val place: String,
    val address: String,
    val city: String,
    val geolocation: GeoLocationUI?,
    val isOnline: Boolean
)
