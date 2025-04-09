package ru.dansh1nv.quiz.list.presentation.composable.bottomSheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.designsystem.theme.utils.color.CustomColorModel
import ru.dansh1nv.designsystem.theme.utils.color.toTextColor
import ru.dansh1nv.quiz.list.models.sorting.Sort
import ru.dansh1nv.quiz.list.presentation.BottomSheetEvent
import ru.dansh1nv.quiz.list.presentation.QuizListEvent

@Composable
internal fun SortingBottomSheet(
    onUIEvent: (QuizListEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(Sort.entries) { item ->
            Text(
                text = stringResource(id = item.titleRes),
                style = QuizHubTheme.typography.titleMedium,
                color = CustomColorModel.Surface.toTextColor(),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable { onUIEvent(BottomSheetEvent.OnSortClick(item)) }
            )
        }
    }
}