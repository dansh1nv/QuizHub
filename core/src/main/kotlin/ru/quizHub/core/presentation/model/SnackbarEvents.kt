package ru.quizHub.core.presentation.model

sealed class SnackbarEvents {
    data class ShowErrorSnackbar(val message: String) : SnackbarEvents()
}