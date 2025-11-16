package ru.quizHub.quizList.mappers

import kotlinx.datetime.LocalDateTime
import ru.quizHub.quizApi.model.rudagames.RudaGamesDTO
import ru.quizHub.quizList.utils.MonthConverter
import ru.quizHub.quizlist.models.RudaGames
import ru.quizHub.quizlist.models.Status
import ru.quizHub.quizlist.models.common.GameDate
import ru.quizHub.quizlist.models.common.GameType
import ru.quizHub.quizlist.models.common.Location
import ru.quizHub.quizlist.models.common.PaymentMethod

class RudaGamesDataMapper {

    private companion object {
        const val CASH_AND_CARD = "Оплата наличными и картой"
        const val CLASSIC = "Классическая"
    }

    fun mapToRudaGames(dtos: List<RudaGamesDTO>): List<RudaGames> {
        return dtos.map { dto -> map(dto) }
    }

    fun map(dto: RudaGamesDTO): RudaGames {
        return RudaGames(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            image = dto.image?.head.orEmpty(),
            gameType = mapGameType(dto.gameType.orEmpty()),
            formatDate = mapGameDate(dto.datetime.orEmpty()),
            formatTime = dto.time,
            price = dto.price?.div(100),
            currency = dto.currency,
            paymentMethod = mapPaymentMethod(dto.paymentType.orEmpty()),
            location = mapLocation(dto),
            status = mapStatus(dto.status.orEmpty()),
            minMembersCount = dto.minMembersCount,
            maxMembersCount = dto.maxMembersCount
        )
    }

    private fun mapGameDate(formatDate: String): GameDate {
        val date = LocalDateTime.parse(formatDate)
        return GameDate(
            dateTime = date,
            day = date.date.dayOfMonth.toString(),
            month = MonthConverter.getMonthNameByNumber(date.monthNumber),
            time = date.time.toString()
        )
    }

    private fun mapLocation(dto: RudaGamesDTO): Location {
        return Location(
            name = dto.location,
            latitude = null,
            longitude = null,
            address = dto.address,
            city = dto.city
        )
    }

    //Возвращаем Null если нет совпадений (на сайте все что не "reserve" - обычная регистрация)
    private fun mapStatus(statusText: String): Status? {
        return Status.entries.firstOrNull { statusText == it.rudaGamesId }
    }

    //На сайте либо "Оплата наличными и картой", либо null (ничего про способ оплаты не пишут)
    private fun mapPaymentMethod(paymentMethod: String): PaymentMethod? {
        return when (paymentMethod) {
            CASH_AND_CARD -> PaymentMethod.CASH_AND_CARD
            else -> null
        }
    }

    private fun mapGameType(gameType: String): GameType {
        return when (gameType) {
            CLASSIC -> GameType.CLASSIC
            else -> GameType.THEMATIC
        }
    }
}