package ru.dansh1nv.quiz.list.presentation.composable.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.sorting.Sort

@Composable
internal fun SortingIndication(sort: Sort) {
    Row {
        Text(
            text = stringResource(sort.titleRes),
            style = QuizHubTheme.typography.bodyMedium,
            color = QuizHubTheme.colorScheme.onSurface,
        )
    }
}