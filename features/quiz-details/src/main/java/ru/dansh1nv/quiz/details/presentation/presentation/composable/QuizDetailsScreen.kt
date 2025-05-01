package ru.dansh1nv.quiz.details.presentation.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun QuizDetailsScreen() {
    Column() {
        Text(
            text = "Это экран детализации квиза",
            style = QuizHubTheme.typography.titleLarge,
            color = QuizHubTheme.colorScheme.onSurface
        )
    }
}