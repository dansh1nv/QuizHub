package ru.dansh1nv.designsystem.theme.utils.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.dansh1nv.designsystem.theme.uiKit.QuizHubTheme

data class CustomColorModel(
    val color: ColorType,
    val alpha: Float? = null,
) {
    companion object {
        val Surface = CustomColorModel(ColorType.SURFACE)
    }
}

//TODO: Расширить цветовую палитру
enum class ColorType {
    SURFACE,
}

@Composable
fun CustomColorModel.toIconColor(): Color {
    val color = when (this@toIconColor.color) {
        ColorType.SURFACE -> QuizHubTheme.colorScheme.onSurface
    }
    return alpha?.let { color.copy(alpha = it) } ?: color
}

@Composable
fun CustomColorModel.toTextColor(): Color {
    val color = when(this@toTextColor.color) {
        ColorType.SURFACE -> QuizHubTheme.colorScheme.onSurface
    }
    return alpha?.let { color.copy(alpha = it) } ?: color
}

@Composable
fun CustomColorModel.toBgColor(): Color {
    val color = when(this@toBgColor.color) {
        ColorType.SURFACE -> QuizHubTheme.colorScheme.surface
    }
    return alpha?.let { color.copy(alpha = it) } ?: color
}