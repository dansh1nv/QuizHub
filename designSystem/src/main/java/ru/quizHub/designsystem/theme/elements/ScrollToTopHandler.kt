package ru.quizHub.designsystem.theme.elements

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ScrollToTopHandler(
    listState: LazyListState,
    scrollThresholdToBecomeVisible: Int = 600,
    onVisibilityChanged: (Boolean) -> Unit
) {
    var prevFirstVisibleItemIndex by remember {
        mutableIntStateOf(listState.firstVisibleItemIndex)
    }
    var prevFirstVisibleItemScrollOffset by remember {
        mutableIntStateOf(listState.firstVisibleItemScrollOffset)
    }
    var lastVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(
        key1 = listState.firstVisibleItemIndex,
        key2 = listState.firstVisibleItemScrollOffset
    ) {
        val currentIndex = listState.firstVisibleItemIndex
        val currentScrollOffset = listState.firstVisibleItemScrollOffset
        val isScrollingUp = listState.isScrollingUp(
            prevFirstVisibleItemIndex,
            prevFirstVisibleItemScrollOffset
        )
        val isTop = currentIndex == 0 && currentScrollOffset == 0
        val shouldVisible = when {
            isTop -> false
            isScrollingUp -> false
            else -> {
                val hasScrolledEnough =
                    currentIndex > 0 || currentScrollOffset > scrollThresholdToBecomeVisible
                if (lastVisibility) {
                    true
                } else {
                    hasScrolledEnough
                }
            }
        }
        if (lastVisibility != shouldVisible) {
            lastVisibility = shouldVisible
            onVisibilityChanged(shouldVisible)
        }
        prevFirstVisibleItemIndex = currentIndex
        prevFirstVisibleItemScrollOffset = currentScrollOffset
    }
}

fun LazyListState.isScrollingUp(
    previousFirstVisibleItemIndex: Int,
    previousFirstVisibleItemScrollOffset: Int
): Boolean {
    val currentFirstVisibleItemIndex = this.firstVisibleItemIndex
    val currentFirstVisibleItemScrollOffset = this.firstVisibleItemScrollOffset

    return if (previousFirstVisibleItemIndex != currentFirstVisibleItemIndex) {
        previousFirstVisibleItemIndex > currentFirstVisibleItemIndex
    } else {
        previousFirstVisibleItemScrollOffset > currentFirstVisibleItemScrollOffset
    }
}