package ru.dansh1nv.quiz_list_domain.models

data class ShareEvent(
    val title: String,
    val date: String?,
    val time: String?,
    val teamSize: String?,
    val address: String?,
    val place: String?,
    val price: String
)