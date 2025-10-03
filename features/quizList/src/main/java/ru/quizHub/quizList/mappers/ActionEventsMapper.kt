package ru.quizHub.quizList.mappers

import android.net.Uri
import ru.quizHub.common.StringDividerType
import ru.quizHub.common.formatStringsWithDividerPoints
import ru.quizHub.core.resourceManager.IResourceManager
import ru.quizHub.quizList.R
import ru.quizHub.quizList.models.item.LocationUI
import ru.quizHub.quizList.models.item.QuizUI

class ActionEventsMapper(
    private val resourceManager: IResourceManager,
) {
    fun mapToShareText(quiz: QuizUI): String {
        val isPerson = quiz.priceAdditionalText.contains("с человека", ignoreCase = true)

        return buildString {
            appendLine(
                resourceManager.getStringById(
                    R.string.join_quiz,
                    resourceManager.getStringById(quiz.organization.title)
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
            quiz.location?.address?.takeIf { it.isNotBlank() }?.let { address ->
                appendLine(resourceManager.getStringById(R.string.address_quiz, address))
            }
            quiz.location?.place?.let { place ->
                appendLine(resourceManager.getStringById(R.string.place_quiz, place))
            }
            if (isPerson) {
                appendLine(
                    resourceManager.getStringById(
                        R.string.price_person_quiz,
                        quiz.formatPrice
                    )
                )
            } else {
                appendLine(
                    resourceManager.getStringById(
                        R.string.price_team_quiz,
                        quiz.formatPrice
                    )
                )
            }
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