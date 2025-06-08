package ru.dansh1nv.quiz.list.mappers

import android.net.Uri
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.item.QuizUI

class ActionEventsMapper(private val resourceManager: IResourceManager) {
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
        //TODO: сделать обработку случая, если place = "Онлайн"
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

        val address = buildString {
            append(location.address.takeIf { it.isNotBlank() } ?: "")
            location.place.takeIf { it.isNotBlank() }?.let { place ->
                if (isNotEmpty()) append(", $place")
            }
        }.takeIf { it.isNotBlank() } ?: return ""

        return GEO_URI_PREFIX + Uri.encode(address)
    }

    companion object {
        const val GEO_URI_PREFIX = "geo:0,0?q="
        const val GEO_URI_WITH_COORDINATES = "geo:%s,%s?q=%s, %s"
        const val TEXT_TWO_PARTS = "%s: %s"
    }
}