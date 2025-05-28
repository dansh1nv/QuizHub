package ru.dansh1nv.quiz_list_domain.models.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ActionEventsListener {
    private val actionEvents = MutableSharedFlow<ActionEvents>(extraBufferCapacity = 1)

    fun onActionEvent(actionEvent: ActionEvents) {
        actionEvents.tryEmit(actionEvent)
    }

    fun observerActionEvents(): SharedFlow<ActionEvents> {
        return actionEvents
    }
}