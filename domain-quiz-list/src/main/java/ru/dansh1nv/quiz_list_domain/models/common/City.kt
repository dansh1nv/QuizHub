package ru.dansh1nv.quiz_list_domain.models.common

data class City(
    val id: Long,
    val name: String,
    val squizId: Long?,
    val quizPleaseId: Long?,
    val shakerQuizId: String?,
)
