package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.quizHub.core.presentation.calendar.clickable
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.quizList.R
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.ScreenEvent

@Composable
internal fun ResetFiltersButton(
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable { onUIEvent(ScreenEvent.ResetFilters) }
    ) {
        Text(
            text = stringResource(R.string.reset_filters),
            style = QuizHubTheme.typography.bodyMedium,
            color = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterVertically),
        )

        Icon(
            painter = painterResource(UIDrawable.ic_remix_close),
            tint = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically),
            contentDescription = null,
        )
    }
}