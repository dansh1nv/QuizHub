package ru.quizHub.designsystem.theme.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

enum class TagColor {
    ORANGE,
    PURPLE,
    BLUE,
    RED,
    GREEN,
}

@Composable
fun TagColor.toBackgroundColor(): Color {
    return when (this) {
        TagColor.ORANGE -> QuizHubTheme.customColor.orange
        TagColor.PURPLE -> QuizHubTheme.customColor.purple
        TagColor.BLUE -> QuizHubTheme.customColor.blue
        TagColor.RED -> QuizHubTheme.customColor.red
        TagColor.GREEN -> QuizHubTheme.customColor.green
    }
}

@Composable
fun TagColor.toTextColor(): Color {
    return when (this) {
        TagColor.ORANGE -> QuizHubTheme.customColor.darkPurple
        TagColor.PURPLE -> QuizHubTheme.customColor.white
        TagColor.BLUE -> QuizHubTheme.customColor.white
        TagColor.RED -> QuizHubTheme.customColor.white
        TagColor.GREEN -> QuizHubTheme.customColor.darkPurple
    }
}