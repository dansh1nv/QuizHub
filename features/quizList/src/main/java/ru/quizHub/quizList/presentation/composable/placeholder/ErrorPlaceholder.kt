package ru.quizHub.quizList.presentation.composable.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.R
import ru.quizHub.quizList.presentation.ScreenEvent

@Composable
internal fun ErrorPlaceholder(
    errorText: String = stringResource(id = R.string.empty_placeholder_text),
    onUIEvent: (ScreenEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 16.dp
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            style = QuizHubTheme.typography.titleLarge,
            text = errorText,
            textAlign = TextAlign.Center,
            color = QuizHubTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        //Добавить проверку на VPN
        Text(
            style = QuizHubTheme.typography.titleMedium,
            text = stringResource(id = R.string.vpn_warning),
            textAlign = TextAlign.Center,
            color = QuizHubTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { onUIEvent(ScreenEvent.OnRefresh) }
        ) {
            Text(
                text = stringResource(id = R.string.refresh),
                style = QuizHubTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}