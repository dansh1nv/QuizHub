package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dansh1nv.quiz.list.models.item.QuizUI
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.composable.card.QuizCard

@Composable
internal fun QuizListContent(
    quizList: List<QuizUI>,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(quizList) { quiz ->
            QuizCard(
                quizGame = quiz,
                onUIEvent = onUIEvent,
            )
        }
    }
}