package ru.dansh1nv.quiz.list.presentation.composable.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.presentation.ScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FiltersView(
    isFiltersShow: Boolean,
    sheetState: SheetState,
    onUIEvent: (ScreenEvent) -> Unit,
) {
    if (isFiltersShow) {
        ModalBottomSheet(
            onDismissRequest = {
                onUIEvent(ScreenEvent.OnFiltersClick(false))
            },
            sheetState = sheetState
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
                        text = stringResource(id = R.string.filter_title),
                        style = QuizHubTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.filter_reset),
                        modifier = Modifier.clickable {
                            onUIEvent(ScreenEvent.FilterClick(null))
                        }
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ru.dansh1nv.quiz.list.models.filters.Filters.entries) { item ->
                        Text(
                            text = stringResource(id = item.titleRes),
                            style = QuizHubTheme.typography.titleMedium,
                            modifier = Modifier.clickable {
                                onUIEvent(ScreenEvent.FilterClick(item.organization))
                            },
                        )
                    }
                }
            }
        }
    }
}