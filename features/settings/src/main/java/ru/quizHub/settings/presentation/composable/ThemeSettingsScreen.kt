package ru.quizHub.settings.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.settings.R
import ru.quizHub.settings.presentation.ThemeManager
import ru.quizHub.settings.presentation.composable.theme.ThemeOptionsList

@Composable
fun ThemeSettingsScreen() {
    val themeManager: ThemeManager = koinViewModel()
    val themeMode by themeManager.themeMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_theme_title),
            textAlign = TextAlign.Center,
            style = QuizHubTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        ThemeOptionsList(
            localThemeState = themeMode,
            onThemeSelected = { newTheme ->
                themeManager.updateThemeMode(newTheme)
            }
        )
    }
}