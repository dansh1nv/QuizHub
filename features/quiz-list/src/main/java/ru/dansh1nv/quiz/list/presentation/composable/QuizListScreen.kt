package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.dansh1nv.core.navigation.destinations.QuizDetailsDestination
import ru.dansh1nv.core.presentation.model.UIStatus
import ru.dansh1nv.core.presentation.viewModel.viewModel
import ru.dansh1nv.designsystem.theme.bottomsheet.QuizModalBottomSheet
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.bottomsheet.BottomSheetModels
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.QuizListSideEffect
import ru.dansh1nv.quiz.list.presentation.QuizListState
import ru.dansh1nv.quiz.list.presentation.QuizListViewModel
import ru.dansh1nv.quiz.list.presentation.ScreenEvent
import ru.dansh1nv.quiz.list.presentation.composable.bottomSheets.CalendarBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.bottomSheets.CityBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.bottomSheets.FiltersBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.bottomSheets.SortingBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.elements.ResetFiltersButton
import ru.dansh1nv.quiz.list.presentation.composable.elements.SortingIndication
import ru.dansh1nv.quiz.list.presentation.composable.placeholder.EmptyPlaceholder
import ru.dansh1nv.quiz.list.presentation.composable.placeholder.ErrorPlaceholder

@Composable
fun QuizListScreen(navController: NavHostController) {
    val viewModel = viewModel<QuizListViewModel> {
        parametersOf()
    }
    val screenState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is QuizListSideEffect.NavigateQuizDetails -> {
                navController.navigate(QuizDetailsDestination.route)
            }

            QuizListSideEffect.NetworkError -> {}
        }
    }

    BaseScreen(
        screenState = screenState,
        viewmodel = viewModel,
        onUIEvent = viewModel::handleEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseScreen(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
    viewmodel: QuizListViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(
            screenState = screenState,
            onUIEvent = onUIEvent,
        )
        when (screenState.uiStatus) {
            UIStatus.Loading -> {
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

            is UIStatus.Error -> {
                ErrorPlaceholder(
                    errorText = screenState.uiStatus.errorText,
                    onUIEvent = onUIEvent,
                )
            }

            UIStatus.Empty -> {
                EmptyPlaceholder()
            }

            is UIStatus.Loaded -> {
                if (screenState.featureToggle.isFavouriteFeatureEnabled) {
                    TabLayout(
                        selectedTabIndex = screenState.selectedTabIndex,
                        onEvent = onUIEvent,
                    )
                }
                //Подумать над дизайном
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    SortingIndication(screenState.sort, onUIEvent)
                    if (screenState.filtersState.isApplied) {
                        ResetFiltersButton(onUIEvent)
                    }
                }

                PullToRefreshBox(
                    isRefreshing = screenState.uiStatus == UIStatus.Loading,
                    onRefresh = { onUIEvent(ScreenEvent.OnRefresh) },
                    content = {
                        QuizListContent(
                            quizList = screenState.quizList,
                            onUIEvent = onUIEvent,
                        )
                    }
                )
            }
        }
    }

    QuizModalBottomSheet(
        controller = viewmodel,
        customBottomSheetContent = { bottomSheet ->
            when (bottomSheet) {
                is BottomSheetModels.FilterBottomSheetModel -> {
                    FiltersBottomSheet(onUIEvent = onUIEvent)
                }

                is BottomSheetModels.SortingBottomSheetModel -> {
                    SortingBottomSheet(onUIEvent = onUIEvent)
                }

                is BottomSheetModels.CalendarBottomSheetModel -> {
                    CalendarBottomSheet(
                        events = bottomSheet.events,
                        onUIEvent = onUIEvent,
                    )
                }

                is BottomSheetModels.CityBottomSheetModel -> {
                    CityBottomSheet(
                        screenState = screenState,
                        onUIEvent = onUIEvent
                    )
                }
            }
        }
    )
}