package ru.dansh1nv.core.presentation.model

sealed class ActionEvents {
    data class ShareEvent(val shareText: String) : ActionEvents()
}