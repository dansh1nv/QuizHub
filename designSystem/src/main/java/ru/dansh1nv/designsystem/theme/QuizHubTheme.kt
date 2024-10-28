package ru.dansh1nv.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }
internal val LocalTypography = staticCompositionLocalOf { QuizHubTypography }
internal val LocalShapes = staticCompositionLocalOf { Shapes() }
internal val LocalIconTintColor = compositionLocalOf { Color.Black }
internal val LocalTagColor = compositionLocalOf { customColor() }

object QuizHubTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val tintColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalIconTintColor.current

    val customColor: CustomColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalTagColor.current
}

@Composable
fun QuizHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    QuizHubTheme(
        darkTheme = darkTheme,
        dynamicColor = true,
        content = content,
    )
}

@Composable
private fun QuizHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    shapes: Shapes = QuizHubTheme.shapes,
    typography: Typography = QuizHubTheme.typography,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
    val tintColor = colorScheme.onSurfaceVariant
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = typography
    ) {
        CompositionLocalProvider(
            LocalColorScheme provides colorScheme,
            LocalShapes provides shapes,
            LocalTypography provides typography,
            LocalIconTintColor provides tintColor,
        ) {
            ProvideTextStyle(value = typography.bodyLarge, content = content)
        }
    }
}

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)