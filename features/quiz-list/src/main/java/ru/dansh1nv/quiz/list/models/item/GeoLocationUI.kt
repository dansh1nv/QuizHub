package ru.dansh1nv.quiz.list.models.item

data class GeoLocationUI(
    val latitude: String,
    val longitude: String,
    val locationText: String,
) {
    companion object {
        val EMPTY = GeoLocationUI(
            latitude = "",
            longitude = "",
            locationText = ""
        )
    }
}