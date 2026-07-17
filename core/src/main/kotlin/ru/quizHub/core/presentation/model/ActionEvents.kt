package ru.quizHub.core.presentation.model

sealed class ActionEvents {
    data class ShareEvent(val shareText: String) : ActionEvents()
    data class ShowLocationEvent(val locationText: String) : ActionEvents()
    data class AddToCalendarEvent(
        val title: String,
        val description: String,
        val location: String,
        val beginTimeMillis: Long,
        val endTimeMillis: Long,
    ) : ActionEvents()
}