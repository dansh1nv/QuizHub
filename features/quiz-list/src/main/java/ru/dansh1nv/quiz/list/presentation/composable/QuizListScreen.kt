package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.dansh1nv.core.navigation.destinations.QuizDetailsDestination
import ru.dansh1nv.core.presentation.model.UIStatus
import ru.dansh1nv.designsystem.theme.bottomsheet.QuizModalBottomSheet
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.models.bottomsheet.BottomSheetModels
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.QuizListSideEffect
import ru.dansh1nv.quiz.list.presentation.QuizListState
import ru.dansh1nv.quiz.list.presentation.QuizListViewModel
import ru.dansh1nv.quiz.list.presentation.composable.bottomSheets.CalendarBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.bottomSheets.FiltersBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.bottomSheets.SortingBottomSheet
import ru.dansh1nv.quiz.list.presentation.composable.placeholder.ErrorPlaceholder

@Composable
fun QuizListScreen(navController: NavHostController) {
    val viewModel = koinViewModel<QuizListViewModel>()
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

@Composable
internal fun BaseScreen(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
    viewmodel: QuizListViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Header(
            //TODO:Заменить заглушку города на список с городами
            //TODO:Подключить геолокацию
            city = CityModel("Санкт-Петербург", 2L),
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

            UIStatus.Error -> {
                ErrorPlaceholder(onUIEvent = onUIEvent)
            }

            UIStatus.Loaded -> {
                if (screenState.featureToggle.isFavouriteFeatureEnabled) {
                    TabLayout(
                        selectedTabIndex = screenState.selectedTabIndex,
                        onEvent = onUIEvent,
                    )
                }
                QuizListContent(
                    quizList = screenState.quizList,
                    onUIEvent = onUIEvent,
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
                    CalendarBottomSheet()
                }
            }
        }
    )
}