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
fun TagColor.toColor(): Color {
    return when(this) {
        TagColor.ORANGE -> QuizHubTheme.tagColor.orange
        TagColor.PURPLE -> QuizHubTheme.tagColor.purple
        TagColor.BLUE -> QuizHubTheme.tagColor.blue
    }
}