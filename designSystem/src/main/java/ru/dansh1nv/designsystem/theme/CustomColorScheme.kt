package ru.dansh1nv.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorScheme(
    val orange: Color,
    val purple: Color,
    val blue: Color,
    val red: Color,
    val yellow: Color,
    val green: Color,
    val darkBlue: Color,
    val dartRed: Color,
    val white: Color,
    val contrastBlue: Color,
    val darkPurple: Color,
)

fun customColor() = CustomColorScheme(
    orange = Color(0xFFFBAD1E),
    purple = Color(0xFF9A41FE),
    blue = Color(0xFF5067DE),
    red = Color(0xFFb00d37),
    yellow = Color(0xFFF8E059),
    green = Color(0xFF2ADB70),
    darkBlue = Color(0xFF2435A6),
    dartRed = Color(0xFF712D46),
    white = Color(0xFFECD7e6),
    contrastBlue = Color(0xFF2506F3),
    darkPurple = Color(0xFF6B138D),
)