package ru.quizHub.settings.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
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
        ThemeOptionsList(
            localThemeState = themeMode,
            onThemeSelected = { newTheme ->
                themeManager.updateThemeMode(newTheme)
            }
        )
    }
}