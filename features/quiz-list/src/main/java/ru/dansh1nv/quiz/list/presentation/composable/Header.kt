package ru.dansh1nv.quiz.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.dansh1nv.designsystem.theme.QuizHubTheme
import ru.dansh1nv.quiz.list.R
import ru.dansh1nv.quiz.list.models.CityModel
import ru.dansh1nv.quiz.list.presentation.QuizListEvent
import ru.dansh1nv.quiz.list.presentation.ScreenEvent
import ru.dansh1nv.quiz.list.presentation.State

@Composable
internal fun Header(
    screenState: State,
    city: CityModel,
    onUIEvent: (QuizListEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .background(color = QuizHubTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable { onUIEvent(ScreenEvent.OnLocationClick) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
            )
            Text(
                text = city.name,
                style = QuizHubTheme.typography.titleMedium,
                color = QuizHubTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (screenState is State.Loaded) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onUIEvent(ScreenEvent.OnFiltersButtonClick) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filters),
                        contentDescription = null,
                        tint = QuizHubTheme.colorScheme.onSurface,
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onUIEvent(ScreenEvent.OnSortButtonClick) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sort),
                        contentDescription = null,
                        tint = QuizHubTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}