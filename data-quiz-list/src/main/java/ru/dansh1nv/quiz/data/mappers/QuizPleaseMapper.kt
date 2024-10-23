package ru.dansh1nv.quiz.data.mappers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import ru.dansh1nv.quiz.data.utils.MonthConverter
import ru.dansh1nv.quiz_list_domain.models.GameDate
import ru.dansh1nv.quiz_list_domain.models.GameFormat
import ru.dansh1nv.quiz_list_domain.models.PaymentMethod
import ru.dansh1nv.quiz_list_domain.models.QuizPlease
import ru.dansh1nv.quiz_list_domain.models.Status
import ru.dansh1nv.quizapi.model.quizplease.QuizPleaseDTO
import ru.dansh1nv.quizapi.model.quizplease.StatusDTO

object QuizPleaseMapper {

    private const val DIFFICULTY_TAG =
        """<div class=\\"badge-difficulty__title badge-difficulty__title_registration\\">\\n"""
    private const val BASE_URL = "https://quizplease.ru"

    fun mapToQuiz(dtos: List<QuizPleaseDTO>): List<QuizPlease> {
        return dtos.map(::map)
    }

    private fun map(dto: QuizPleaseDTO): QuizPlease {
        val dateParts = dto.formatDate?.split(" ") ?: emptyList()
        return QuizPlease(
            id = dto.id,
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
            location = dto.location,
            address = dto.address,
            city = dto.city,
            latitude = dto.latitude,
            longitude = dto.longitude,
            difficulty = dto.difficulty?.let { mapDifficulty(it) },
            status = mapStatus(dto.status),
            paymentMethod = dto.paymentMethod?.let { mapPaymentMethod(it) },
        )
    }

    private fun mapStatus(status: StatusDTO?): Status? {
        return Status.entries.firstOrNull { it.id == status?.id }
    }

    private fun mapGameFormat(gameFormat: Int): GameFormat? {
        return GameFormat.entries.firstOrNull { it.id == gameFormat }
    }

    private fun mapPaymentMethod(paymentMethod: Int): PaymentMethod? {
        return PaymentMethod.entries.firstOrNull { it.id == paymentMethod }
    }

    private fun mapDifficulty(difficulty: String): String? {
        return Regex("""$DIFFICULTY_TAG (.*?)""")
            .find(difficulty)
            ?.groupValues
            ?.getOrNull(1)
    }

    //"23.10.24 19:30"
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
            dayOfTheWeek = "",
            time = time,
        )
    }

}

object DateFormatter {
    const val DATE_FORMAT_DD_MM_YY_HH_MM = "dd.MM.yy HH:mm"
}