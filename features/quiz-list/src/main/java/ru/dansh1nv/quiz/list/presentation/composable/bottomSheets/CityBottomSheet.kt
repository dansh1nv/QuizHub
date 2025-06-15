package ru.dansh1nv.quiz.list.presentation.composable.bottomSheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.elements.search.CustomSearchBar
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.QuizListState
import ru.dansh1nv.quiz.list.presentation.ScreenEvent

@Composable
internal fun CityBottomSheet(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
) {

    var searchQuery by remember { mutableStateOf("") }
    val searchResults by remember(searchQuery) {
        derivedStateOf { screenState.cities.filter { it.isSearchVisible } }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        CustomSearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                onUIEvent(ScreenEvent.OnSearch(searchQuery))
            },
            onClear = {
                searchQuery = ""
                onUIEvent(ScreenEvent.OnSearch(searchQuery))
            },
            keyboardController = keyboardController,
            focusRequester = focusRequester,
            focusManager = focusManager,
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(searchResults) { item ->
                CityItem(
                    item = item,
                    onUIEvent = onUIEvent,
                )
            }
        }
    }
}

@Composable
private fun CityItem(
    item: CityModel,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clickable { onUIEvent(ScreenEvent.OnCityClick(item)) }
    ) {
        Text(
            text = item.name,
            style = QuizHubTheme.typography.bodyLarge,
            color = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            thickness = 1.dp,
            color = QuizHubTheme.colorScheme.onSurface,
        )
    }
}