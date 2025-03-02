package wildberries.wbpartners.utils.keyboard

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.SoftwareKeyboardController

@Composable
fun keyboardAsState(): State<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = keypadHeight > screenHeight * 0.15
        }
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HideKeyboardAndClearFocusOnScroll(
    keyboardController: SoftwareKeyboardController?,
    localFocusManager: FocusManager,
    scrollState: ScrollableState
) {
    val isKeyboardOpen by keyboardAsState()

    val needHideKeyboard by remember {
        derivedStateOf { scrollState.isScrollInProgress && isKeyboardOpen }
    }

    LaunchedEffect(key1 = needHideKeyboard) {
        if (needHideKeyboard) {
            keyboardController?.hide()
            localFocusManager.clearFocus()
        }
    }
}