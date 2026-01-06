package ru.quizHub.settings.presentation.composable.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.list.SingleSelectableListItem
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.settings.R
import ru.quizHub.settings.models.ThemeModeUI

@Composable
fun ThemeOptionsList(
    localThemeState: ThemeModeUI,
    onThemeSelected: (ThemeModeUI) -> Unit
) {
    val themeOptions = listOf(
        ThemeOptionItem(
            title = stringResource(R.string.theme_option_light),
            themeMode = ThemeModeUI.Light
        ),
        ThemeOptionItem(
            title = stringResource(R.string.theme_option_dark),
            themeMode = ThemeModeUI.Dark
        ),
        ThemeOptionItem(
            title = stringResource(R.string.theme_option_system),
            themeMode = ThemeModeUI.System
        ),
        ThemeOptionItem(
            title = stringResource(R.string.theme_option_high_contrast),
            themeMode = ThemeModeUI.HighContrast
        )
    )

    themeOptions.forEachIndexed { index, option ->
        SingleSelectableListItem(
            text = option.title,
            item = option.themeMode,
            localState = mutableStateOf(localThemeState),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            onClick = { onThemeSelected(option.themeMode) }
        )
        if (index < themeOptions.lastIndex) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = QuizHubTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            )
        }
    }
}

private data class ThemeOptionItem(
    val title: String,
    val themeMode: ThemeModeUI
)