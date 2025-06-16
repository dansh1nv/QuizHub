package ru.dansh1nv.core

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Context.startIntentSafe(
    intent: Intent,
    onFailure: (IntentError) -> Unit = { it.showSnackbar(this) }
) {
    runCatching {
        startActivity(intent)
    }.onFailure { throwable ->
        val error = when (throwable) {
            is ActivityNotFoundException -> IntentError.ActivityNotFound
            is IllegalArgumentException -> IntentError.IllegalArgument
            is SecurityException -> IntentError.Security
            else -> IntentError.Unknown(throwable)
        }
        onFailure(error)
    }
}

fun Context.showSnackbarMessage(message: String) {
    val rootView = (this as? Activity)?.window?.decorView?.findViewById<View>(android.R.id.content)
    rootView?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
    }
}