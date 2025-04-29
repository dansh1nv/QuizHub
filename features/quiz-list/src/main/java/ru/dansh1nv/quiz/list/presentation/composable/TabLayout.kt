package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.TabModel
import ru.dansh1nv.quiz.list.presentation.ScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TabLayout(
    selectedTabIndex: Int,
    onEvent: (ScreenEvent) -> Unit,
) {
    SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
        TabModel.entries.forEach { tab ->
            Tab(
                selected = selectedTabIndex == tab.index,
                selectedContentColor = QuizHubTheme.colorScheme.primary,
                unselectedContentColor = QuizHubTheme.colorScheme.onSurface,
                onClick = { onEvent(ScreenEvent.OnTabClick(tab.index)) },
            ) {
                Text(
                    text = stringResource(id = tab.titleRes),
                    style = QuizHubTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }
    }
}