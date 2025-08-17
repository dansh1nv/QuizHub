package ru.quizHub.core.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.quizHub.core.presentation.model.ActionEvents

class ActionEventsListener {
    private val actionEvents = MutableSharedFlow<ActionEvents>(extraBufferCapacity = 1)

    fun onActionEvent(actionEvent: ActionEvents) {
        actionEvents.tryEmit(actionEvent)
    }

    fun observerActionEvents(): SharedFlow<ActionEvents> {
        return actionEvents
    }
}