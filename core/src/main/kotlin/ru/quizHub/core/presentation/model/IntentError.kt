package ru.quizHub.core.presentation.model

sealed class IntentError {
    data object ActivityNotFound : IntentError()
    data object IllegalArgument : IntentError()
    data object Security : IntentError()
    data object GeoLocationError : IntentError()
    data class Unknown(val exception: Throwable) : IntentError()
}