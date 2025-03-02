package ru.dansh1nv.designsystem.theme.elements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun TextCell(
    text: String,
    color: Color = QuizHubTheme.colorScheme.onSurface,
    style: TextStyle = QuizHubTheme.typography.bodyMedium,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        style = style,
        modifier = modifier,
        textAlign = textAlign,
    )
}