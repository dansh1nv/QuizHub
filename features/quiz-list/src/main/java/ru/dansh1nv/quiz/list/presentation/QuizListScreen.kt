package ru.dansh1nv.quiz.list.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.presentation.composable.Header
import ru.dansh1nv.quiz.list.presentation.composable.QuizListContent
import ru.dansh1nv.quiz.list.presentation.composable.TabLayout
import ru.dansh1nv.quiz.list.presentation.composable.placeholder.ErrorPlaceholder

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

@Composable
internal fun BaseScreen(
    screenState: State,
    onUIEvent: (ScreenEvent) -> Unit,
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
                        color = MaterialTheme.colorScheme.secondary,
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
            }
        }
    }
}