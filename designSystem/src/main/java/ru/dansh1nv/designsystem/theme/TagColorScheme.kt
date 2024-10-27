package ru.dansh1nv.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TagColorScheme(
    val orange: Color,
    val purple: Color,
    val blue: Color,
)

fun tagColor() = TagColorScheme(
    orange = Color(0xFFFF8058),
    purple = Color(0xFF9A41FE),
    blue = Color(0xFF5067DE)
)