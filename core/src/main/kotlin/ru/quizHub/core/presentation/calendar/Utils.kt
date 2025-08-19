package ru.quizHub.core.presentation.calendar

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import ru.quizHub.core.presentation.calendar.ContinuousSelectionHelper.isInDateBetweenSelection
import ru.quizHub.core.presentation.calendar.ContinuousSelectionHelper.isOutDateBetweenSelection
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme

fun Modifier.clickable(
    enabled: Boolean = true,
    showRipple: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = if (showRipple) LocalIndication.current else null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )
}

/**
 * Alternative way to find the first fully visible month in the layout.
 *
 * @see [rememberFirstVisibleMonthAfterScroll]
 * @see [rememberFirstMostVisibleMonth]
 */
@Composable
fun rememberFirstCompletelyVisibleMonth(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    // Only take non-null values as null will be produced when the
    // list is mid-scroll as no index will be completely visible.
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.completelyVisibleMonths.firstOrNull() }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

/**
 * Returns the first visible month in a paged calendar **after** scrolling stops.
 *
 * @see [rememberFirstCompletelyVisibleMonth]
 * @see [rememberFirstMostVisibleMonth]
 */
@Composable
fun rememberFirstVisibleMonthAfterScroll(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleMonth.value = state.firstVisibleMonth }
    }
    return visibleMonth.value
}

/**
 * Find the first month on the calendar visible up to the given [viewportPercent] size.
 *
 * @see [rememberFirstCompletelyVisibleMonth]
 * @see [rememberFirstVisibleMonthAfterScroll]
 */
@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private val CalendarLayoutInfo.completelyVisibleMonths: List<CalendarMonth>
    get() {
        val visibleItemsInfo = this.visibleMonthsInfo.toMutableList()
        return if (visibleItemsInfo.isEmpty()) {
            emptyList()
        } else {
            val lastItem = visibleItemsInfo.last()
            val viewportSize = this.viewportEndOffset + this.viewportStartOffset
            if (lastItem.offset + lastItem.size > viewportSize) {
                visibleItemsInfo.removeAt(visibleItemsInfo.lastIndex)
            }
            val firstItem = visibleItemsInfo.firstOrNull()
            if (firstItem != null && firstItem.offset < this.viewportStartOffset) {
                visibleItemsInfo.removeAt(0)
            }
            visibleItemsInfo.map { it.month }
        }
    }

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}

private class HalfSizeShape(private val clipStart: Boolean) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val half = size.width / 2f
        val offset = if (layoutDirection == LayoutDirection.Ltr) {
            if (clipStart) Offset(half, 0f) else Offset.Zero
        } else {
            if (clipStart) Offset.Zero else Offset(half, 0f)
        }
        return Outline.Rectangle(Rect(offset, Size(half, size.height)))
    }
}

fun Modifier.backgroundHighlight(
    day: CalendarDay,
    selection: DateSelection,
    selectionColor: Color,
    continuousSelectionColor: Color,
    textColor: (Color) -> Unit,
): Modifier = composed {
    val (startDate, endDate) = selection
    val padding = 4.dp
    val normalTextColor = QuizHubTheme.colorScheme.onSurface
    val transparentColor = QuizHubTheme.colorScheme.surface
    val isSelected = day.date == startDate
    val isBetween =
        startDate != null && endDate != null && (day.date > startDate && day.date < endDate)
    when (day.position) {
        DayPosition.MonthDate -> {
            when {
                startDate == day.date && endDate == null -> {
                    textColor(normalTextColor)
                    padding(padding)
                        .background(color = selectionColor, shape = CircleShape)
                }

                isSelected -> {
                    textColor(normalTextColor)
                    padding(vertical = padding)
                        .background(
                            color = continuousSelectionColor,
                            shape = HalfSizeShape(clipStart = true),
                        )
                        .padding(horizontal = padding)
                        .background(color = selectionColor, shape = CircleShape)
                }

                isBetween -> {
                    textColor(normalTextColor)
                    padding(vertical = padding)
                        .background(color = continuousSelectionColor)
                }

                day.date == endDate -> {
                    textColor(normalTextColor)
                    padding(vertical = padding)
                        .background(
                            color = continuousSelectionColor,
                            shape = HalfSizeShape(clipStart = false),
                        )
                        .padding(horizontal = padding)
                        .background(color = selectionColor, shape = CircleShape)
                }

                else -> {
                    textColor(normalTextColor)
                    this
                }
            }
        }

        DayPosition.InDate -> {
            textColor(transparentColor)
            if (startDate != null
                && endDate != null
                && isInDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
                    .background(color = continuousSelectionColor)
            } else {
                this
            }
        }

        DayPosition.OutDate -> {
            textColor(transparentColor)
            if (startDate != null
                && endDate != null
                && isOutDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
                    .background(color = continuousSelectionColor)
            } else {
                this
            }
        }
    }
}