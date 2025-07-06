package ru.dansh1nv.quiz.list.presentation.composable.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.elements.QuizHubButton
import ru.dansh1nv.designsystem.theme.elements.search.CustomSearchBar
import ru.dansh1nv.designsystem.theme.list.SingleSelectableListItem
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.QuizListState
import ru.dansh1nv.quiz.list.presentation.ScreenEvent
import ru.dansh1nv.designsystem.R.string as UIString

@Composable
internal fun CityBottomSheet(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
) {

    var footerHeight by remember { mutableIntStateOf(0) }
    val footerHeightToDp by remember { mutableStateOf(footerHeight.dp + 12.dp) }
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by remember(searchQuery) {
        derivedStateOf { screenState.cities.filter { it.isSearchVisible } }
    }
    val localSelectedCity = remember { mutableStateOf(screenState.currentCity) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = footerHeightToDp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = footerHeightToDp),
            ) {
                itemsIndexed(searchResults) { index, item ->
                    SingleSelectableListItem(
                        text = item.name,
                        item = item,
                        localState = localSelectedCity,
                    )

                    if (searchResults.lastIndex != index) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = QuizHubTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(QuizHubTheme.colorScheme.surfaceContainer)
                .onSizeChanged { footerHeight = it.height }
                .align(Alignment.BottomCenter)
        ) {
            QuizHubButton(
                title = stringResource(UIString.select_button_text),
                modifier = Modifier.fillMaxWidth(),
                isEnabled = localSelectedCity.value != screenState.currentCity,
                onClick = { onUIEvent(ScreenEvent.OnCityClick(localSelectedCity.value)) },
            )
        }
    }
}