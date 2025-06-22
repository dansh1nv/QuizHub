package ru.dansh1nv.quiz.list.presentation.composable.bottomSheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import ru.dansh1nv.designsystem.theme.elements.QuizHubRadioButton
import ru.dansh1nv.designsystem.theme.elements.search.CustomSearchBar
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.CityModel
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
        Column(modifier = Modifier.fillMaxSize()) {
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
                    .padding(bottom = footerHeightToDp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(searchResults) { index, item ->
                    CityItem(
                        item = item,
                        localSelectedCity = localSelectedCity,
                    )

                    if (searchResults.lastIndex != index) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            thickness = 1.dp,
                            color = QuizHubTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            QuizHubButton(
                title = stringResource(UIString.select_button_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { footerHeight = it.height },
                isEnabled = localSelectedCity.value != screenState.currentCity,
                onClick = { onUIEvent(ScreenEvent.OnCityClick(localSelectedCity.value)) },
            )
        }
    }
}

@Composable
private fun CityItem(
    item: CityModel,
    localSelectedCity: MutableState<CityModel>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clickable { localSelectedCity.value = item },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = item.name,
            style = QuizHubTheme.typography.bodyLarge,
            color = QuizHubTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
        )

        QuizHubRadioButton(isSelected = localSelectedCity.value == item)
    }
}