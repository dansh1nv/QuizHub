package ru.quizHub.quizList.presentation.composable.bottomSheets

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
import ru.quizHub.designsystem.theme.list.SingleSelectableListItem
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.models.filters.Filters
import ru.quizHub.quizList.presentation.BottomSheetEvent
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.QuizListState

@Composable
internal fun FiltersBottomSheet(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    val localState = remember { mutableStateOf(screenState.filtersState.filters) }
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        itemsIndexed(Filters.entries) { index, item ->
            SingleSelectableListItem(
                text = stringResource(id = item.titleRes),
                item = item,
                localState = localState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
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