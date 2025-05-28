package ru.dansh1nv.quiz_list_domain.models.common

import ru.dansh1nv.quiz_list_domain.models.ShareEvent

sealed class ActionEvents {
    data class ActionEvent(val shareEvent: ShareEvent) : ActionEvents()
}