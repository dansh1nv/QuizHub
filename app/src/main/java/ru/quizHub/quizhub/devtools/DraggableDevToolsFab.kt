package ru.quizHub.quizhub.devtools

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.quizHub.designsystem.theme.uiKit.QuizHubTheme
import ru.quizHub.designsystem.theme.utils.`typealias`.UIDrawable
import kotlin.math.roundToInt

@Composable
fun DraggableDevToolsFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val edgePaddingPx = with(density) { 16.dp.toPx() }

    var parentSize by remember { mutableStateOf(IntSize.Zero) }
    var fabSize by remember { mutableStateOf(IntSize.Zero) }
    var offsetX by remember { mutableFloatStateOf(Float.NaN) }
    var offsetY by remember { mutableFloatStateOf(Float.NaN) }
    var wasDragged by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { parentSize = it }
    ) {
        val maxX = (parentSize.width - fabSize.width).coerceAtLeast(0).toFloat()
        val maxY = (parentSize.height - fabSize.height).coerceAtLeast(0).toFloat()

        val resolvedX = when {
            offsetX.isNaN() && fabSize != IntSize.Zero -> (maxX - edgePaddingPx).coerceAtLeast(0f)
            offsetX.isNaN() -> 0f
            else -> offsetX.coerceIn(0f, maxX)
        }
        val resolvedY = when {
            offsetY.isNaN() && fabSize != IntSize.Zero -> (maxY - edgePaddingPx).coerceAtLeast(0f)
            offsetY.isNaN() -> 0f
            else -> offsetY.coerceIn(0f, maxY)
        }

        FloatingActionButton(
            onClick = {
                if (!wasDragged) onClick()
                wasDragged = false
            },
            modifier = Modifier
                .onSizeChanged { size ->
                    fabSize = size
                    if (offsetX.isNaN() && parentSize != IntSize.Zero) {
                        offsetX = (parentSize.width - size.width - edgePaddingPx).coerceAtLeast(0f)
                        offsetY = (parentSize.height - size.height - edgePaddingPx).coerceAtLeast(0f)
                    }
                }
                .offset { IntOffset(resolvedX.roundToInt(), resolvedY.roundToInt()) }
                .pointerInput(parentSize, fabSize) {
                    val touchSlop = viewConfiguration.touchSlop
                    var totalDrag = 0f
                    detectDragGestures(
                        onDragStart = {
                            wasDragged = false
                            totalDrag = 0f
                        },
                        onDragEnd = { },
                        onDragCancel = { },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            totalDrag += kotlin.math.abs(dragAmount.x) + kotlin.math.abs(dragAmount.y)
                            if (totalDrag > touchSlop) {
                                wasDragged = true
                            }
                            val currentX = if (offsetX.isNaN()) resolvedX else offsetX
                            val currentY = if (offsetY.isNaN()) resolvedY else offsetY
                            offsetX = (currentX + dragAmount.x).coerceIn(0f, maxX)
                            offsetY = (currentY + dragAmount.y).coerceIn(0f, maxY)
                        },
                    )
                },
            containerColor = QuizHubTheme.colorScheme.secondaryContainer,
            contentColor = QuizHubTheme.colorScheme.onSecondaryContainer,
        ) {
            Icon(
                painter = painterResource(UIDrawable.ic_remix_settings_fill),
                contentDescription = "DevTools",
            )
        }
    }
}