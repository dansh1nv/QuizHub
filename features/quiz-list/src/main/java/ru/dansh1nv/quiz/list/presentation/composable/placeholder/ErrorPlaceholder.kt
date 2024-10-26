package ru.dansh1nv.quiz.list.presentation.composable.placeholder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.presentation.ScreenEvent

@Composable
internal fun ErrorPlaceholder(
    onUIEvent: (ScreenEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 16.dp
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.empty_placeholder_text),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.vpn_warning),
                textAlign = TextAlign.Center,
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { onUIEvent(ScreenEvent.OnRefresh) }
            ) {
                Text(
                    text = stringResource(id = R.string.refresh),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}