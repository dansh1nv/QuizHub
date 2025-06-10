package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.core.presentation.calendar.clickable
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.utils.`typealias`.UIDrawable
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.ScreenEvent

@Composable
internal fun ResetFiltersButton(
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable {
            onUIEvent(ScreenEvent.ResetFilters)
        }
    ) {
        Text(
            text = stringResource(R.string.reset_filters),
            style = QuizHubTheme.typography.bodyMedium,
            color = QuizHubTheme.colorScheme.onSurface,
        )

        Icon(
            painter = painterResource(UIDrawable.ic_clear),
            tint = QuizHubTheme.colorScheme.onSurface,
            contentDescription = null,
        )
    }
}