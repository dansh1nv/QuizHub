package ru.dansh1nv.quiz.data.models

import ru.dansh1nv.quiz_list_domain.models.common.City
import ru.dansh1nv.quiz_list_domain.models.common.CityId

enum class CityDTO(
    val quizPleaseId: Long?,
    val shakerQuizId: String?,
    val squizId: Long?,
) {
    MOSCOW(
        quizPleaseId = null,
        shakerQuizId = null,
        squizId = null,
    ),
    SPB(
        quizPleaseId = 17L,
        squizId = 111979372401L,
        shakerQuizId = "b489621b-cfb2-4aef-8c22-02daf19fa08f",
    ),
    KRASNODAR(
        squizId = null,
        quizPleaseId = 37L,
        shakerQuizId = null,
    ),
    UNKNOWN(
        quizPleaseId = null,
        shakerQuizId = null,
        squizId = null,
    )
}

fun City.toDTO(): CityDTO {
    return when(this.id) {
        CityId.MOSCOW -> CityDTO.MOSCOW
        CityId.SPB -> CityDTO.SPB
        CityId.KRASNODAR -> CityDTO.KRASNODAR
        CityId.UNKNOWN -> CityDTO.UNKNOWN
    }
}
