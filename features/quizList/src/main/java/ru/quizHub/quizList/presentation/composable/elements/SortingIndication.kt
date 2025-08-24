package ru.quizHub.quizList.presentation.composable.elements

import androidx.compose.foundation.clickable
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
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.quizList.models.sorting.Sort
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.ScreenEvent

@Composable
internal fun SortingIndication(
    sort: Sort,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Row(
        modifier = Modifier.clickable { onUIEvent(ScreenEvent.OnSortButtonClick) },
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = UIDrawable.ic_sort),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically),
            tint = QuizHubTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(sort.titleRes),
            style = QuizHubTheme.typography.bodyMedium,
            color = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}