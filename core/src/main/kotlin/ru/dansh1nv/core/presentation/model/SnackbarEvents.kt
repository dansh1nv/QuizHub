package ru.dansh1nv.core.presentation.model

sealed class SnackbarEvents {
    data class ShowErrorSnackbar(val message: String) : SnackbarEvents()
}