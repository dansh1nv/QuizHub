package ru.quizHub.profile.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

@Composable
fun ProfileScreen() {
    Column {
        Text(
            text = "Профиль",
            style = QuizHubTheme.typography.titleLarge,
            color = QuizHubTheme.colorScheme.onSurface
        )
    }
}