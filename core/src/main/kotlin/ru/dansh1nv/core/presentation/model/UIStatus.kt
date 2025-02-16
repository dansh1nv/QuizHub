package ru.dansh1nv.core.presentation.model

sealed class UIStatus {
    data object Loaded : UIStatus()
    data object Error: UIStatus()
    data object Loading: UIStatus()
}