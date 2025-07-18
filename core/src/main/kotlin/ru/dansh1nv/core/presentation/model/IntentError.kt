package ru.dansh1nv.core.presentation.model

sealed class IntentError {
    data object ActivityNotFound : IntentError()
    data object IllegalArgument : IntentError()
    data object Security : IntentError()
    data object NoSuchElementException : IntentError()
    data class Unknown(val exception: Throwable) : IntentError()
}