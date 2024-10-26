package ru.dansh1nv.quiz.list.mappers

import ru.dansh1nv.common.orZero
import ru.dansh1nv.common.utils.localeDate.localeDay
import ru.dansh1nv.core.resourceManager.IResourceManager
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.GameDateUI
import ru.dansh1nv.quiz.list.models.Location
import ru.dansh1nv.quiz.list.models.Organization
import ru.dansh1nv.quiz.list.models.QuizUI
import ru.dansh1nv.quiz_list_domain.models.GameFormat
import ru.dansh1nv.quiz_list_domain.models.GameType
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import java.time.format.TextStyle
import java.util.Locale

internal class QuizPleaseMapper(
    private val resourceManager: IResourceManager
) {

    fun mapToQuizPlease(entities: List<QuizPlease>): List<QuizUI> {
        return entities.map(::mapToQuizPlease)
    }

    fun mapToQuizPlease(entity: QuizPlease): QuizUI {
        return QuizUI(
            id = entity.id.orZero(),
            city = entity.city.orEmpty(),
            theme = buildString {
                entity.title?.let {
                    append("$it, ")
                }
                entity.packageNumber?.let { append(it) }
            },
            formattedDate = GameDateUI(
                date = entity.formatDate?.dateTime,
                dateText = "${entity.formatDate?.day} ${entity.formatDate?.month}",
                timeWithDay = buildString {
                    entity.formatTime?.let { append("$it, ") }
                    entity.formatDate?.dateTime?.dayOfWeek?.let { append(localeDay(it)) }
                },
            ),
            formatPrice = entity.formatPrice.orEmpty(),
            description = entity.description.orEmpty(),
            place = entity.location.orEmpty(),
            image = entity.image.orEmpty(),
            address = entity.address.orEmpty(),
            difficulty = entity.difficulty.orEmpty(),
            location = Location(
                latitude = entity.latitude.orEmpty(),
                longitude = entity.longitude.orEmpty(),
                locationText = "${entity.latitude.orEmpty()},${entity.longitude.orEmpty()}"
            ),
            format = entity.gameFormat ?: GameFormat.OFFLINE,
            packageNumber = entity.packageNumber.orEmpty(),
            paymentMethod = entity.paymentMethod?.title.orEmpty(),
            status = entity.status?.title.orEmpty(),
            organization = Organization.QUIZ_PLEASE,
            additionDescription = "",
            priceAdditionalText = when (entity.gameFormat) {
                GameFormat.ONLINE -> {
                    resourceManager.getStringById(R.string.quiz_item_price_for_team)
                }

                GameFormat.OFFLINE -> {
                    resourceManager.getStringById(R.string.quiz_item_price_for_people)
                }

                else -> ""
            },
            type = GameType.CLASSIC,
        )
    }
}