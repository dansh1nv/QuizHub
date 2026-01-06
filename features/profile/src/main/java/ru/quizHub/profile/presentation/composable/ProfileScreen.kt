package ru.quizHub.profile.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.profile.R

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Text(
            text = stringResource(R.string.profile_screen_title),
            style = QuizHubTheme.typography.titleLarge,
            color = QuizHubTheme.colorScheme.onSurface
        )
    }
}