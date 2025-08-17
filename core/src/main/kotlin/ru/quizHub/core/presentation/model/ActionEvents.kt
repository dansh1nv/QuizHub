package ru.quizHub.core.presentation.model

sealed class ActionEvents {
    data class ShareEvent(val shareText: String) : ActionEvents()
    data class ShowLocationEvent(val locationText: String) : ActionEvents()
}