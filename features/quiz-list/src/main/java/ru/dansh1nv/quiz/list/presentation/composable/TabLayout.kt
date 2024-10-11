package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.quiz.list.presentation.UIEvent

@Composable
internal fun TabLayout(
    tabs: List<String>,
    onEvent: (UIEvent) -> Unit,
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(QuizHubTheme.colorScheme.surfaceContainer)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        items(tabs) {
            Tab(it)
        }
    }
}

@Composable
internal fun Tab(title: String) {
    Card {
        Text(
            text = title,
            color = QuizHubTheme.colorScheme.onSurface,
            style = QuizHubTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
    }
}