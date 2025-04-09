package ru.dansh1nv.designsystem.theme.utils.updateEffect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

@Composable
fun UpdateEffect(
    vararg keys: Any,
    block: suspend CoroutineScope.() -> Unit,
) {
    var isTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(keys) {
        if (isTriggered) {
            block()
        } else {
            isTriggered = true
        }
    }
}
