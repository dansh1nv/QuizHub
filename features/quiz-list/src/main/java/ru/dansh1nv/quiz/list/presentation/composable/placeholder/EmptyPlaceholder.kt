package ru.dansh1nv.quiz.list.presentation.composable.placeholder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.R

@Composable
internal fun EmptyPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.empty_placeholder_city_not_selected),
            style = QuizHubTheme.typography.titleLarge,
            color = QuizHubTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}