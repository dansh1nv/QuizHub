package ru.quizHub.settings.presentation

import ru.quizHub.core.presentation.UIEvent

internal sealed class ScreenEvent : UIEvent {
    data object OnThemeClick : ScreenEvent()
}