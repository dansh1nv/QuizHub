package ru.quizHub.quizList.mappers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import ru.quizHub.quizApi.model.quizplease.QuizPleaseDTO
import ru.quizHub.quizList.utils.MonthConverter
import ru.quizHub.quizlist.models.Difficulty
import ru.quizHub.quizlist.models.QuizPlease
import ru.quizHub.quizlist.models.Status
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameFormat
import ru.quizHub.quizlist.models.common.Location
import ru.quizHub.quizlist.models.common.PaymentMethod

class QuizPleaseDataMapper {

    fun mapToQuiz(dtos: List<QuizPleaseDTO>): List<QuizPlease> {
        return dtos.map(::map)
    }

    fun map(dto: QuizPleaseDTO) = QuizPlease(
        id = dto.id.orEmpty(),
        title = dto.title,
        packageNumber = "#${dto.gameNumber}",
        description = dto.quote ?: dto.description,
        image = dto.template?.backgroundTablet,
        gameFormat = dto.gameType?.let(::mapGameFormat),
        datetime = dto.date,
        formatDate = mapGameDate(dto.date.orEmpty()),
        price = dto.price,
        formatPrice = dto.currentPrice,
        location = Location(
            city = dto.place?.city?.title,
            latitude = dto.place?.lat,
            longitude = dto.place?.lon,
            address = dto.place?.address,
            name = dto.place?.title,
        ),
        difficulty = mapDifficulty(dto.level ?: dto.template?.gameLevel),
        status = mapStatus(dto.status),
        paymentMethod = dto.payMethod?.let(::mapPaymentMethod),
    )

    private fun mapDifficulty(level: String?): Difficulty? {
        val normalizedLevel = level?.lowercase() ?: return null
        return Difficulty.entries.firstOrNull { it.name.lowercase() == normalizedLevel }
    }

    private fun mapStatus(status: Int?): Status? {
        return Status.entries.firstOrNull { it.quizPleaseId.contains(status) }
    }

    private fun mapGameFormat(gameFormat: Int): GameFormat? {
        return GameFormat.entries.firstOrNull { it.id == gameFormat }
    }

    private fun mapPaymentMethod(paymentMethod: Int): PaymentMethod? {
        return PaymentMethod.entries.firstOrNull { it.id == paymentMethod }
    }

    private fun mapGameDate(datetime: String): GameDate {
        val (date, time) = datetime.split(" ", limit = 2)
        val timeArray = time.split(":", limit = 2)
        val dateArray = date.split(".", limit = 3)
        val day = dateArray.getOrNull(0)?.toInt() ?: 1
        val month = dateArray.getOrNull(1)?.toInt() ?: 1
        val yearRaw = dateArray.getOrNull(2).orEmpty()
        val year = when (yearRaw.length) {
            2 -> "20$yearRaw".toInt()
            else -> yearRaw.toIntOrNull() ?: 1970
        }
        val localTime = LocalTime(
            hour = timeArray.getOrNull(0)?.toInt() ?: 0,
            minute = timeArray.getOrNull(1)?.toInt() ?: 0,
        )
        val localDate = LocalDate(
            year = year,
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