package ru.dansh1nv.quiz.list.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.androidx.compose.koinViewModel
import ru.dansh1nv.navigation.SharedScreen
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.presentation.composable.Header
import ru.dansh1nv.quiz.list.presentation.composable.QuizListContent
import ru.dansh1nv.quiz.list.presentation.composable.TabLayout

class QuizListScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<QuizListViewModel>()
        val screenState by viewModel.state.collectAsStateWithLifecycle()
        val uiEvent = viewModel::onUIEvent

        Box {
            when (screenState) {
                is State.Loading -> {
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

                is State.Error -> {

                }

                is State.Success -> {
                    val state = (screenState as State.Success)
                    Column {
                        Header(
                            city = CityModel("Ваш город", 2L),
                            onEvent = uiEvent
                        )
                        HorizontalDivider()
                        TabLayout(
                            tabs = listOf("SQuiz", "Квиз, Плиз!"),
                            onEvent = uiEvent
                        )
                        HorizontalDivider()
                        QuizListContent(state.quizList)
                    }
                }
            }
        }
    }
}