package ru.dansh1nv.quiz.list.presentation

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import org.orbitmvi.orbit.compose.collectAsState
import ru.dansh1nv.core.presentation.model.UIStatus
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.presentation.composable.Header
import ru.dansh1nv.quiz.list.presentation.composable.QuizListContent
import ru.dansh1nv.quiz.list.presentation.composable.TabLayout
import ru.dansh1nv.quiz.list.presentation.composable.placeholder.ErrorPlaceholder

class QuizListScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<QuizListViewModel>()
        val screenState by viewModel.collectAsState()
        val onUIEvent = viewModel::handleEvent

        BaseScreen(
            screenState = screenState,
            onUIEvent = onUIEvent
        )
    }
}

@Composable
internal fun BaseScreen(
    screenState: QuizListState,
    onUIEvent: (QuizListEvent) -> Unit,
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
                QuizListContent(screenState.quizList)
            }
        }
    }
}