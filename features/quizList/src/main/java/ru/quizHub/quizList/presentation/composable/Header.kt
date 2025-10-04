package ru.quizHub.quizList.presentation.composable

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
import ru.quizHub.core.presentation.model.UIStatus
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import ru.quizHub.quizList.presentation.QuizListEvent
import ru.quizHub.quizList.presentation.QuizListState
import ru.quizHub.quizList.presentation.ScreenEvent

@Composable
internal fun Header(
    screenState: QuizListState,
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
                painter = painterResource(id = UIDrawable.ic_remix_location),
                contentDescription = null,
                tint = QuizHubTheme.colorScheme.onSurface,
            )
            Text(
                text = screenState.currentCity.name,
                style = QuizHubTheme.typography.titleMedium,
                color = QuizHubTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (screenState.uiStatus is UIStatus.Loaded) {
                if (screenState.featureToggle.isCalendarFeatureEnable) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onUIEvent(ScreenEvent.OnCalendarClick) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = UIDrawable.ic_remix_calendar_fill),
                            contentDescription = null,
                            tint = QuizHubTheme.colorScheme.onSurface
                        )
                    }
                }
                if (screenState.featureToggle.isFiltersFeatureEnabled) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onUIEvent(ScreenEvent.OnFiltersButtonClick) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = UIDrawable.ic_remix_filter_fill),
                            contentDescription = null,
                            tint = QuizHubTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}