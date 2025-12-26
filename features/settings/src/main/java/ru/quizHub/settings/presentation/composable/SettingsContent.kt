package ru.quizHub.settings.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.list.SettingsListItem
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.designsystem.theme.utils.`typealias`.UIString
import ru.quizHub.settings.R
import ru.quizHub.settings.presentation.ScreenEvent

@Composable
internal fun SettingsContent(onUIEvent: (ScreenEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = QuizHubTheme.typography.headlineMedium,
            color = QuizHubTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingsListItem(
            title = stringResource(UIString.settings_theme_title),
            iconResId = UIDrawable.ic_contrast,
            onClick = { onUIEvent(ScreenEvent.OnThemeClick) }
        )
    }
}