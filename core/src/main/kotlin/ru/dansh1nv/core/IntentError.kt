package ru.dansh1nv.core

import android.content.Context

sealed class IntentError {
    data object ActivityNotFound : IntentError()
    data object IllegalArgument : IntentError()
    data object Security : IntentError()
    data class Unknown(val exception: Throwable) : IntentError()

    private fun toMessage(context: Context): String = when (this) {
        ActivityNotFound -> context.getString(R.string.error_activity_not_found)
        IllegalArgument -> context.getString(R.string.error_illegal_argument)
        Security -> context.getString(R.string.error_security)
        is Unknown -> context.getString(R.string.error_unknown)
    }

    fun showSnackbar(context: Context) {
        context.showSnackbarMessage(toMessage(context))
    }
}