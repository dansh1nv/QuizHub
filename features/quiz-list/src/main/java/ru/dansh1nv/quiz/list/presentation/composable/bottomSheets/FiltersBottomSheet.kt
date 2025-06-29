package ru.dansh1nv.quiz.list.presentation.composable.bottomSheets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.list.SingleSelectableListItem
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.filters.Filters
import ru.dansh1nv.quiz.list.presentation.BottomSheetEvent
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.QuizListState

@Composable
internal fun FiltersBottomSheet(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    val localState = remember { mutableStateOf(screenState.filtersState.filters) }
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        itemsIndexed(Filters.entries) { index, item ->
            SingleSelectableListItem(
                text = stringResource(id = item.titleRes),
                item = item,
                localState = localState,
                onClick = { onUIEvent(BottomSheetEvent.OnFilterClick(item)) }
            )
            if (Filters.entries.lastIndex != index) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = QuizHubTheme.colorScheme.onSurface,
                )
            }
        }
    }
}