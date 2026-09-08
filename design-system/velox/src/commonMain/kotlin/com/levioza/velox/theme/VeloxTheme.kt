package com.levioza.velox.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

/** The default light Material 3 color scheme for Velox. */
val VeloxLightColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFF4F46E5),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0E7FF),
    onPrimaryContainer = Color(0xFF1E1B4B),
    secondary = Color(0xFF0F766E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCCFBF1),
    onSecondaryContainer = Color(0xFF042F2E),
    background = Color(0xFFFCFCFF),
    surface = Color(0xFFFCFCFF),
)

/** The default dark Material 3 color scheme for Velox. */
val VeloxDarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFFC7D2FE),
    onPrimary = Color(0xFF312E81),
    primaryContainer = Color(0xFF4338CA),
    onPrimaryContainer = Color(0xFFE0E7FF),
    secondary = Color(0xFF99F6E4),
    onSecondary = Color(0xFF134E4A),
    secondaryContainer = Color(0xFF115E59),
    onSecondaryContainer = Color(0xFFCCFBF1),
)

/**
 * Applies Velox's Material 3 colors and Readex Pro type scale to [content].
 *
 * @param darkTheme Whether the dark color scheme is used, or null to infer it.
 * @param colorScheme Optional color-scheme override.
 * @param typography Optional typography override; Readex Pro is used by default.
 * @param content Content rendered inside the theme.
 */
@Composable
fun VeloxTheme(
    darkTheme: Boolean? = null,
    colorScheme: ColorScheme? = null,
    typography: Typography? = null,
    content: @Composable () -> Unit,
) {
    val resolvedDarkTheme = darkTheme ?: (MaterialTheme.colorScheme.background.luminance() < 0.5f)
    val resolvedColorScheme = colorScheme ?: if (resolvedDarkTheme) VeloxDarkColorScheme else VeloxLightColorScheme
    val resolvedTypography = typography ?: VeloxTypography()

    MaterialTheme(
        colorScheme = resolvedColorScheme,
        typography = resolvedTypography,
        content = content,
    )
}
