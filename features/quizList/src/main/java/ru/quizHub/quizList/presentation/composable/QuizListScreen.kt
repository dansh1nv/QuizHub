package ru.quizHub.quizList.presentation.composable

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.quizHub.core.navigation.destinations.QuizDetailsDestination
import ru.quizHub.core.presentation.model.UIStatus
import ru.quizHub.core.presentation.viewModel.viewModel
import ru.quizHub.designsystem.theme.bottomsheet.QuizModalBottomSheet
import ru.quizHub.designsystem.theme.elements.ScrollToTopButton
import ru.quizHub.designsystem.theme.elements.ScrollToTopHandler
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.quizList.models.bottomsheet.BottomSheetModels
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.QuizListSideEffect
import ru.quizHub.quizList.presentation.QuizListState
import ru.quizHub.quizList.presentation.QuizListViewModel
import ru.quizHub.quizList.presentation.ScreenEvent
import ru.quizHub.quizList.presentation.composable.bottomSheets.CalendarBottomSheet
import ru.quizHub.quizList.presentation.composable.bottomSheets.CityBottomSheet
import ru.quizHub.quizList.presentation.composable.bottomSheets.FiltersBottomSheet
import ru.quizHub.quizList.presentation.composable.bottomSheets.SortingBottomSheet
import ru.quizHub.quizList.presentation.composable.elements.ResetFiltersButton
import ru.quizHub.quizList.presentation.composable.elements.SortingIndication
import ru.quizHub.quizList.presentation.composable.placeholder.EmptyCityPlaceholder
import ru.quizHub.quizList.presentation.composable.placeholder.ErrorPlaceholder

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
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    ScrollToTopHandler(
        listState = listState,
        onVisibilityChanged = { show ->
            onUIEvent(ScreenEvent.OnScrollPositionChanged(show))
        }
    )

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
                EmptyCityPlaceholder(onUIEvent)
            }

            is UIStatus.Loaded -> {
                if (screenState.featureToggle.isFavouriteFeatureEnabled) {
                    TabLayout(
                        selectedTabIndex = screenState.selectedTabIndex,
                        onEvent = onUIEvent,
                    )
                }

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

                Box(modifier = Modifier.weight(1f)) {
                    PullToRefreshBox(
                        isRefreshing = screenState.uiStatus == UIStatus.Loading,
                        onRefresh = { onUIEvent(ScreenEvent.OnRefresh) },
                        content = {
                            QuizListContent(
                                quizList = screenState.quizList,
                                isCardDetailsEnabled = screenState.featureToggle.cardDetails,
                                onUIEvent = onUIEvent,
                                listState = listState,
                            )
                        }
                    )
                    ScrollToTopButton(
                        visible = screenState.isScrollUpVisible,
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                    )
                }
            }
        }
    }

    QuizModalBottomSheet(
        controller = viewmodel,
        customBottomSheetContent = { bottomSheet ->
            when (bottomSheet) {
                is BottomSheetModels.FilterBottomSheetModel -> {
                    FiltersBottomSheet(
                        screenState = screenState,
                        onUIEvent = onUIEvent,
                    )
                }

                is BottomSheetModels.SortingBottomSheetModel -> {
                    SortingBottomSheet(
                        screenState = screenState,
                        onUIEvent = onUIEvent,
                    )
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