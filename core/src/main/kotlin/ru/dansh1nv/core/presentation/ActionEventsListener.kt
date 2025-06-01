package ru.dansh1nv.core.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.dansh1nv.core.presentation.model.ActionEvents

class ActionEventsListener {
    private val actionEvents = MutableSharedFlow<ActionEvents>(extraBufferCapacity = 1)

    fun onActionEvent(actionEvent: ActionEvents) {
        actionEvents.tryEmit(actionEvent)
    }

    fun observerActionEvents(): SharedFlow<ActionEvents> {
        return actionEvents
    }
}