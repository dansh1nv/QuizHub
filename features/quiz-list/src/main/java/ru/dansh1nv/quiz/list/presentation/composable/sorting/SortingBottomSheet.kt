package ru.dansh1nv.quiz.list.presentation.composable.sorting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.sorting.Sort
import ru.dansh1nv.quiz.list.presentation.BottomSheetEvent
import ru.dansh1nv.quiz.list.presentation.QuizListEvent

@Composable
internal fun SortingBottomSheet(
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.sorting_title),
                style = QuizHubTheme.typography.titleLarge,
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Sort.entries) { item ->
                Text(
                    text = stringResource(id = item.titleRes),
                    style = QuizHubTheme.typography.titleMedium,
                    modifier = Modifier
                        .clickable {
                            onUIEvent(BottomSheetEvent.OnSortClick(item))
                        }
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
        }
    }
}