package ru.dansh1nv.core.presentation.model

import android.content.Context
import android.content.Intent

fun Context.startIntentSafe(intent: Intent, onFailure: (Throwable) -> Unit = {}) {
    runCatching {
        startActivity(intent)
    }.onFailure {
        onFailure(it)
    }
}