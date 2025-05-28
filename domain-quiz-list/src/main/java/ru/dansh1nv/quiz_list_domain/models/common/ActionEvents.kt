package ru.dansh1nv.quiz_list_domain.models.common

sealed class ActionEvents {
    data class ShareEvent(val shareText: String) : ActionEvents()
}