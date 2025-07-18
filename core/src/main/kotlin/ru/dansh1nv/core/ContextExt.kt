package ru.dansh1nv.core

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import ru.dansh1nv.core.presentation.IntentErrorMapper
import ru.dansh1nv.core.presentation.model.IntentError

fun Context.startIntentSafe(
    intent: Intent,
    intentErrorMapper: IntentErrorMapper,
    onFailure: (String) -> Unit = {}
) {
    runCatching {
        startActivity(intent)
    }.onFailure { exception ->
        val intentError = when (exception) {
            is ActivityNotFoundException -> IntentError.ActivityNotFound
            is IllegalArgumentException -> IntentError.IllegalArgument
            is SecurityException -> IntentError.Security
            is NoSuchElementException -> IntentError.GeoLocationError
            else -> IntentError.Unknown(exception)
        }
        val errorMessage = intentErrorMapper.mapErrorMessage(intentError)
        onFailure(errorMessage)
    }
}