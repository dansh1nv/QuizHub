package ru.dansh1nv.designsystem.theme.elements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import ru.dansh1nv.designsystem.theme.QuizHubTheme

@Composable
fun TextCell(
    text: String,
    style: TextStyle = QuizHubTheme.typography.bodyMedium,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = QuizHubTheme.colorScheme.onSurface,
        style = style,
        modifier = modifier,
    )
}