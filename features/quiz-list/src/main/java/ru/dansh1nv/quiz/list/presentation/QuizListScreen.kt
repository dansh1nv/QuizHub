package ru.dansh1nv.quiz.list.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.models.bottomsheet.BottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.Header
import ru.dansh1nv.quiz.list.presentation.composable.QuizListContent
import ru.dansh1nv.quiz.list.presentation.composable.TabLayout
import ru.dansh1nv.quiz.list.presentation.composable.filters.FiltersBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.location.LocationBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.placeholder.ErrorPlaceholder
import ru.dansh1nv.quiz.list.presentation.composable.sorting.SortingBottomSheet

class QuizListScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<QuizListViewModel>()
        val screenState by viewModel.state.collectAsStateWithLifecycle()
        val onUIEvent = viewModel::onUIEvent

        BaseScreen(
            screenState = screenState,
            onUIEvent = onUIEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseScreen(
    screenState: State,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val city = if (screenState is State.Loaded) {
            screenState.city ?: CityModel("Санкт-Петербург", 17L)
        } else {
            CityModel("Санкт-Петербург", 17L)
        }
        Header(
            //TODO:Подключить геолокацию
            city = city,
            screenState = screenState,
            onUIEvent = onUIEvent,
        )
        when (screenState) {
            State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(60.dp),
                        color = QuizHubTheme.colorScheme.secondary,
                    )
                }
            }

            State.Error -> {
                ErrorPlaceholder(onUIEvent = onUIEvent)
            }

            is State.Loaded -> {
                if (screenState.featureToggle.isFavouriteFeatureEnabled) {
                    TabLayout(
                        selectedTabIndex = screenState.selectedTabIndex,
                        onEvent = onUIEvent,
                    )
                }
                QuizListContent(screenState.quizList)
                when (screenState.bottomSheet) {
                    is BottomSheet.SortingBottomSheet -> {
                        SortingBottomSheet(
                            sheetState = sheetState,
                            onUIEvent = onUIEvent,
                        )
                    }

                    is BottomSheet.FiltersBottomSheet -> {
                        FiltersBottomSheet(
                            sheetState = sheetState,
                            onUIEvent = onUIEvent
                        )
                    }

                    is BottomSheet.LocationBottomSheet -> {
                        LocationBottomSheet(
                            items = listOf(
                                CityModel("Москва", 1L),
                                CityModel("Санкт-Петербург", 17L),
                                CityModel("Краснодар", 2L)
                            ),
                            sheetState = sheetState,
                            onUIEvent = onUIEvent,
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}