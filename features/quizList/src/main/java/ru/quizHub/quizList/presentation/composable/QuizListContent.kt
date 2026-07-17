package ru.quizHub.quizList.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.quizHub.quizList.models.item.QuizUI
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.composable.card.QuizCard

@Composable
internal fun QuizListContent(
    quizList: List<QuizUI>,
    isCardDetailsEnabled: Boolean,
    onUIEvent: (QuizListEvent) -> Unit,
    listState: LazyListState,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(quizList.filter { it.isVisible }) { quiz ->
            QuizCard(
                quizGame = quiz,
                isCardDetailsEnabled = isCardDetailsEnabled,
                onUIEvent = onUIEvent,
            )
        }
    }
}