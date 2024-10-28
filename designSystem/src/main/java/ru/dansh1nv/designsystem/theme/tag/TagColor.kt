package ru.dansh1nv.designsystem.theme.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.dansh1nv.designsystem.theme.QuizHubTheme

enum class TagColor {
    ORANGE,
    PURPLE,
    BLUE,
}

@Composable
fun TagColor.toBackgroundColor(): Color {
    return when(this) {
        TagColor.ORANGE -> QuizHubTheme.customColor.orange
        TagColor.PURPLE -> QuizHubTheme.customColor.purple
        TagColor.BLUE -> QuizHubTheme.customColor.blue
    }
}

@Composable
fun TagColor.toTextColor(): Color {
    return when(this) {
        TagColor.ORANGE -> QuizHubTheme.customColor.darkPurple
        TagColor.PURPLE -> QuizHubTheme.customColor.white
        TagColor.BLUE -> QuizHubTheme.customColor.white
    }
}