package ru.dansh1nv.quiz.data.mappers

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
        return QuizPlease(
            id = dto.id,
            title = dto.title,
            packageNumber = dto.packageNumber,
            description = dto.description,
            image = BASE_URL + dto.image,
            gameFormat = dto.gameFormat?.let { mapGameFormat(it) },
            datetime = dto.datetime,
            formatDate = dto.formatDate,
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
            paymentMethod = dto.paymentMethod?.let { mapPaymentMethod(it) } ,
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

    private fun mapDifficulty(difficulty: String) : String? {
        return Regex(""""$DIFFICULTY_TAG (.*?)""")
            .find(difficulty)
            ?.groupValues
            ?.getOrNull(1)
    }

}