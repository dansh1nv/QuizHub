package ru.quizHub.quizList.presentation.composable.calendar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.YearMonth
import ru.quizHub.core.presentation.calendar.displayText
import ru.quizHub.designsystem.R
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.color.CustomColorModel
import ru.quizHub.designsystem.theme.utils.color.toIconColor
import ru.quizHub.designsystem.theme.utils.color.toTextColor

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    isHorizontal: Boolean = true,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .background(QuizHubTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            painter  = painterResource(R.drawable.ic_remix_arrow_left),
            contentDescription = "Previous",
            onClick = goToPrevious,
            isHorizontal = isHorizontal,
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = currentMonth.displayText(),
            textAlign = TextAlign.Center,
            style = QuizHubTheme.typography.titleLarge,
            color = CustomColorModel.Surface.toTextColor()
        )
        CalendarNavigationIcon(
            painter = painterResource(R.drawable.ic_remix_arrow_right),
            contentDescription = "Next",
            onClick = goToNext,
            isHorizontal = isHorizontal,
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    painter: Painter,
    contentDescription: String,
    isHorizontal: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(shape = CircleShape)
            .clickable(role = Role.Button, onClick = onClick),
    ) {
        val rotation by animateFloatAsState(
            targetValue = if (isHorizontal) 0f else 90f,
            label = "CalendarNavigationIconAnimation",
        )
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .align(Alignment.Center)
                .rotate(rotation),
            painter = painter,
            contentDescription = contentDescription,
            tint = CustomColorModel.Surface.toIconColor()
        )
    }
}