package ru.quizHub.quizList.presentation.composable.bottomSheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.elements.QuizHubButton
import ru.quizHub.designsystem.theme.list.MultipleSelectableListItem
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIString
import ru.quizHub.quizList.models.item.Organization
import ru.quizHub.quizList.presentation.BottomSheetEvent
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.QuizListState

@Composable
internal fun FiltersBottomSheet(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    val selectedFilters =
        remember { mutableStateListOf(*screenState.filtersState.organizations.toTypedArray()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            itemsIndexed(Organization.entries) { index, item ->
                MultipleSelectableListItem(
                    text = stringResource(id = item.title),
                    isSelected = selectedFilters.contains(item),
                    onClick = {
                        if (selectedFilters.contains(item)) {
                            selectedFilters.remove(item)
                        } else {
                            selectedFilters.add(item)
                        }
                    }
                )
                if (Organization.entries.lastIndex != index) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = QuizHubTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        QuizHubButton(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            title = stringResource(UIString.apply_button_text),
            onClick = { onUIEvent(BottomSheetEvent.OnApplyFiltersClick(selectedFilters)) }
        )
    }
}