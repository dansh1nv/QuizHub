package ru.dansh1nv.quiz.list.models.item

data class LocationUI(
    val place: String,
    val address: String,
    val city: String,
    val geolocation: GeoLocationUI?,
)
