package ru.quizHub.core.presentation.model

sealed class UIStatus {
    data object Empty : UIStatus()
    data class Loaded(val message: String = "") : UIStatus()
    data class Error(val errorText: String) : UIStatus()
    data object Loading : UIStatus()
}