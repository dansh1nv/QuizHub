package ru.quizHub.designsystem.theme.elements

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun QuizHubButton(
    title: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors().copy(
        containerColor = QuizHubTheme.colorScheme.surface,
        contentColor = QuizHubTheme.colorScheme.onSurface,
        disabledContentColor = QuizHubTheme.colorScheme.surface.copy(alpha = 0.1f),
        disabledContainerColor = QuizHubTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    ),
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = onClick,
        enabled = isEnabled,
    ) {
        Text(
            text = title,
            style = QuizHubTheme.typography.titleMedium,
            color = QuizHubTheme.colorScheme.onSurface,
        )
    }
}