package ru.dansh1nv.quiz.list.mappers

import android.net.Uri
import ru.dansh1nv.common.StringDividerType
import ru.dansh1nv.common.formatStringsWithDividerPoints
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.LocationUI
import ru.dansh1nv.quiz.list.models.item.QuizUI

class ActionEventsMapper(
    private val resourceManager: IResourceManager,
) {
    fun mapToShareText(quiz: QuizUI): String {
        return buildString {
            appendLine(
                resourceManager.getStringById(
                    R.string.join_quiz,
                    quiz.organization.toString()
                )
            )
            appendLine()
            quiz.formattedDate?.dateText?.let { date ->
                appendLine(resourceManager.getStringById(R.string.date_quiz, date))
            }
            quiz.formattedDate?.timeWithDay?.let { time ->
                appendLine(resourceManager.getStringById(R.string.time_day_quiz, time))
            }
            quiz.teamSize?.teamSizeText?.let { size ->
                appendLine(resourceManager.getStringById(R.string.team_size_quiz, size))
            }
            quiz.location?.address?.let { address ->
                appendLine(resourceManager.getStringById(R.string.address_quiz, address))
            }
            quiz.location?.place?.let { place ->
                appendLine(resourceManager.getStringById(R.string.place_quiz, place))
            }
            appendLine(resourceManager.getStringById(R.string.price_quiz, quiz.formatPrice))
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
        return formatStringsWithDividerPoints(
            arrayOf(quiz.location?.city, quiz.location?.address),
            StringDividerType.CommaSpace
        )
    }

    private fun buildQueryWithoutCoordinates(location: LocationUI): String {
        return formatStringsWithDividerPoints(
            arrayOf(location.address, location.place),
            StringDividerType.CommaSpace
        ).takeIf { it.isNotBlank() }?.let { GEO_URI_PREFIX + Uri.encode(it) } ?: ""
    }

    companion object {
        const val GEO_URI_PREFIX = "geo:0,0?q="
        const val GEO_URI_WITH_COORDINATES = "geo:%s,%s?q=%s, %s"
    }
}