package ru.quizHub.core.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.quizHub.core.presentation.model.SnackbarEvents

class SnackbarListener {
    private val snackbarMessages = MutableSharedFlow<SnackbarEvents>(extraBufferCapacity = 1)

    fun showSnackbar(message: SnackbarEvents) {
        snackbarMessages.tryEmit(message)
    }

    fun observeSnackbarMessages(): SharedFlow<SnackbarEvents> {
        return snackbarMessages
    }
}