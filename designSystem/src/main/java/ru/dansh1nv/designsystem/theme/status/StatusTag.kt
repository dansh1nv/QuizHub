package ru.dansh1nv.designsystem.theme.status

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.dansh1nv.designsystem.theme.QuizHubTheme

enum class StatusTag {
    REGISTRATION_OPENED,
    RESERVATION,
    REGISTRATION_CLOSED,
}

@Composable
fun StatusTag.toBackgroundColor(): Color {
    return when (this) {
        StatusTag.REGISTRATION_OPENED -> QuizHubTheme.customColor.green
        StatusTag.RESERVATION -> QuizHubTheme.customColor.yellow
        StatusTag.REGISTRATION_CLOSED -> QuizHubTheme.customColor.red
    }
}

@Composable
fun StatusTag.toTextColor(): Color {
    return when(this) {
        StatusTag.REGISTRATION_OPENED -> QuizHubTheme.customColor.contrastBlue
        StatusTag.RESERVATION -> QuizHubTheme.customColor.darkBlue
        StatusTag.REGISTRATION_CLOSED -> QuizHubTheme.customColor.white
    }
}