package ru.quizHub.quizList.mappers

import android.net.Uri
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import ru.quizHub.common.StringDividerType
import ru.quizHub.common.formatStringsWithDividerPoints
import ru.quizHub.core.presentation.model.ActionEvents
import ru.quizHub.core.resourceManager.IResourceManager
import ru.quizHub.quizList.R
import ru.quizHub.quizList.models.item.LocationUI
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizlist.models.common.GameFormat

class ActionEventsMapper(
    private val resourceManager: IResourceManager,
) {
    fun mapToShareText(quiz: QuizUI): String {
        val priceRes = when (quiz.format) {
            GameFormat.OFFLINE -> R.string.price_person_quiz
            GameFormat.ONLINE -> R.string.price_team_quiz
        }
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
            appendLine(
                value = resourceManager.getStringById(priceRes, quiz.formatPrice)
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

    fun mapToAddToCalendarEvent(quiz: QuizUI): ActionEvents.AddToCalendarEvent? {
        val dateTime = quiz.formattedDate?.date ?: return null
        val beginTimeMillis = dateTime
            .toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
        val location = formatStringsWithDividerPoints(
            arrayOf(quiz.location?.place, quiz.location?.address),
            StringDividerType.CommaSpace
        )
        return ActionEvents.AddToCalendarEvent(
            title = quiz.theme,
            description = mapToShareText(quiz),
            location = location,
            beginTimeMillis = beginTimeMillis,
            endTimeMillis = beginTimeMillis + DEFAULT_EVENT_DURATION_MILLIS,
        )
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
        ).takeIf { it.isNotBlank() }?.let { GEO_URI_PREFIX + Uri.encode(it) }.orEmpty()
    }

    companion object {
        const val GEO_URI_PREFIX = "geo:0,0?q="
        const val GEO_URI_WITH_COORDINATES = "geo:%s,%s?q=%s, %s"
        private const val DEFAULT_EVENT_DURATION_MILLIS = (2 * 60 + 30) * 60 * 1000L
    }
}