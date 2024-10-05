package ru.dansh1nv.quiz.list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.dansh1nv.quiz.list.models.QuizPleaseUI
import ru.dansh1nv.quiz.list.models.QuizUI
import ru.dansh1nv.quiz.list.models.SQuizUI
import ru.dansh1nv.quiz.list.presentation.composable.QuizPleaseItem
import ru.dansh1nv.quiz.list.presentation.composable.SQuizGameItem

@Composable
fun QuizListScreen() {
    val viewModel = koinViewModel<QuizListViewModel>()
    val screenState by viewModel.state.collectAsStateWithLifecycle()

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
                QuizListContent((screenState as State.Success).quizList)
            }
        }
    }
}

@Composable
fun QuizListContent(quizList: List<QuizUI>) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(quizList) { quiz ->
            when (quiz) {
                is QuizPleaseUI -> QuizPleaseItem(quiz)
                is SQuizUI -> SQuizGameItem(quiz)
            }
        }
    }
}

@Composable
fun TextCell(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = style,
    )
}