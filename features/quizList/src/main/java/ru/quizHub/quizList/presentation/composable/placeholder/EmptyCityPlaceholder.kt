package ru.quizHub.quizList.presentation.composable.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.elements.QuizHubButton
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.R
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.ScreenEvent

@Composable
internal fun EmptyCityPlaceholder(
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.empty_placeholder_city_not_selected_text),
            style = QuizHubTheme.typography.titleLarge,
            color = QuizHubTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        QuizHubButton(
            title = stringResource(R.string.empty_placeholder_city_not_selected_button),
            onClick = { onUIEvent(ScreenEvent.OnLocationClick) },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = QuizHubTheme.colorScheme.surfaceContainer,
                contentColor = QuizHubTheme.colorScheme.onSurface,
                disabledContentColor = QuizHubTheme.colorScheme.surfaceContainer.copy(alpha = 0.1f),
                disabledContainerColor = QuizHubTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            ),
        )
    }
}