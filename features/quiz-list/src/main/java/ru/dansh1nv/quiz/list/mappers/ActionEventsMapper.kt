package ru.dansh1nv.quiz.list.mappers

import android.net.Uri
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.GeoLocationUI
import ru.dansh1nv.quiz.list.models.item.LocationUI
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz_list_domain.models.common.GeoInfo

class ActionEventsMapper(
    private val resourceManager: IResourceManager,
) {
    fun mapToShareText(quiz: QuizUI): String {
        return buildString {
            appendLine(
                TEXT_TWO_PARTS.format(
                    resourceManager.getStringById(R.string.join_quiz),
                    quiz.organization
                )
            )
            appendLine()
            quiz.formattedDate?.dateText?.let { date ->
                appendLine(
                    TEXT_TWO_PARTS.format(
                        resourceManager.getStringById(R.string.date_quiz),
                        date
                    )
                )
            }
            quiz.formattedDate?.timeWithDay?.let { time ->
                appendLine(
                    TEXT_TWO_PARTS.format(
                        resourceManager.getStringById(R.string.time_day_quiz),
                        time
                    )
                )
            }
            quiz.teamSize?.teamSizeText?.let { size ->
                appendLine(
                    TEXT_TWO_PARTS.format(
                        resourceManager.getStringById(R.string.team_size_quiz),
                        size
                    )
                )
            }
            quiz.location?.address?.let { address ->
                appendLine(
                    TEXT_TWO_PARTS.format(
                        resourceManager.getStringById(R.string.address_quiz),
                        address
                    )
                )
            }
            quiz.location?.place?.let { place ->
                appendLine(
                    TEXT_TWO_PARTS.format(
                        resourceManager.getStringById(R.string.place_quiz),
                        place
                    )
                )
            }
            appendLine(
                TEXT_TWO_PARTS.format(
                    resourceManager.getStringById(R.string.price_quiz),
                    quiz.formatPrice
                )
            )
        }
    }

    fun mapToLocationEventText(quiz: QuizUI): String {
        val location = quiz.location ?: return ""

        location.geolocation?.let { geo ->
            if (geo.latitude.isNotEmpty() && geo.longitude.isNotEmpty()) {
                return String.format(
                    GEO_URI_WITH_COORDINATES,
                    geo.latitude,
                    geo.longitude,
                    Uri.encode(location.address),
                    Uri.encode(location.place)
                )
            }
        }
        return buildQueryWithoutCoordinates(location)
    }

    fun buildGeoQuery(quiz: QuizUI): String {
        val location = quiz.location ?: return ""
        return buildString {
            append(location.city.takeIf { it.isNotBlank() } ?: "")
            location.address.takeIf { it.isNotBlank() }?.let { address ->
                append(", $address")
            }
        }.takeIf { it.isNotBlank() } ?: return ""
    }

    fun updateGeoLocation(
        location: LocationUI,
        geoInfo: GeoInfo
    ): LocationUI {
        val lat = geoInfo.latitude.toString()
        val lon = geoInfo.longitude.toString()
        return location.copy(
            geolocation = GeoLocationUI(
                latitude = lat,
                longitude = lon,
                locationText = "${lat},${lon}"
            )
        )
    }

    private fun buildQueryWithoutCoordinates(location: LocationUI): String {
        return buildString {
            location.address.takeIf { it.isNotBlank() }?.let { address ->
                append(address)
            }
            location.place.takeIf { it.isNotBlank() }?.let { place ->
                append(", $place")
            }
        }.takeIf { it.isNotBlank() }?.let { GEO_URI_PREFIX + Uri.encode(it) } ?: ""
    }

    companion object {
        const val GEO_URI_PREFIX = "geo:0,0?q="
        const val GEO_URI_WITH_COORDINATES = "geo:%s,%s?q=%s, %s"
        const val TEXT_TWO_PARTS = "%s: %s"
    }
}