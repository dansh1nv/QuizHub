package ru.dansh1nv.quiz.data.mappers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import ru.dansh1nv.quiz.data.utils.MonthConverter
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.Status
import ru.dansh1nv.quiz_list_domain.models.common.GameDate
import ru.dansh1nv.quiz_list_domain.models.common.GameFormat
import ru.dansh1nv.quiz_list_domain.models.common.Location
import ru.dansh1nv.quiz_list_domain.models.common.PaymentMethod
import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseDTO
import ru.dansh1nv.quizapi.model.quizplease.StatusDTO

class QuizPleaseDataMapper {

    companion object {
        private const val DIFFICULTY_TAG =
            """<div class="badge-difficulty__title badge-difficulty__title_registration">"""
        private const val CLOSE_TAG = "</div>"
        private const val BASE_URL = "https://quizplease.ru"
    }

    fun mapToQuiz(dtos: List<QuizPleaseDTO>): List<QuizPlease> {
        return dtos.map(::map)
    }

    private fun map(dto: QuizPleaseDTO) = QuizPlease(
        id = dto.id?.toString().orEmpty(),
        title = dto.title,
        packageNumber = dto.packageNumber,
        description = dto.description,
        image = BASE_URL + dto.image,
        gameFormat = dto.gameFormat?.let { mapGameFormat(it) },
        datetime = dto.datetime,
        formatDate = mapGameDate(dto.datetime.orEmpty()),
        formatTime = dto.formatTime,
        price = dto.price,
        formatPrice = dto.formatPrice,
        location = Location(
            city = dto.city,
            latitude = dto.latitude?.toDouble(),
            longitude = dto.longitude?.toDouble(),
            address = dto.address,
            name = dto.location,
        ),
        difficulty = dto.difficulty?.let { mapDifficulty(it) },
        status = mapStatus(dto.status),
        paymentMethod = dto.paymentMethod?.let { mapPaymentMethod(it) },
    )

    private fun mapStatus(status: StatusDTO?): Status? {
        return Status.entries.firstOrNull { it.quizPleaseId == status?.id }
    }

    private fun mapGameFormat(gameFormat: Int): GameFormat? {
        return GameFormat.entries.firstOrNull { it.id == gameFormat }
    }

    private fun mapPaymentMethod(paymentMethod: Int): PaymentMethod? {
        return PaymentMethod.entries.firstOrNull { it.id == paymentMethod }
    }

    private fun mapDifficulty(difficulty: String): String {
        return difficulty.substringAfter(DIFFICULTY_TAG)
            .substringBefore(CLOSE_TAG)
            .trim()
    }

    private fun mapGameDate(
        datetime: String
    ): GameDate {
        val (date, time) = datetime.split(" ", limit = 2)
        val timeArray = time.split(":", limit = 2)
        val dateArray = date.split(".", limit = 3)
        val day = dateArray.getOrNull(0)?.toInt() ?: 1
        val month = dateArray.getOrNull(1)?.toInt() ?: 1
        val localTime = LocalTime(
            hour = timeArray[0].toInt(),
            minute = timeArray[1].toInt(),
        )
        val localDate = LocalDate(
            //TODO: Поправить подсчет даты
            year = "20${dateArray[2]}".toInt(),
            monthNumber = month,
            dayOfMonth = day,
        )
        val localDateTime = LocalDateTime(
            date = localDate,
            time = localTime,
        )
        return GameDate(
            dateTime = localDateTime,
            day = day.toString(),
            month = MonthConverter.getMonthNameByNumber(month),
            time = time,
        )
    }
}