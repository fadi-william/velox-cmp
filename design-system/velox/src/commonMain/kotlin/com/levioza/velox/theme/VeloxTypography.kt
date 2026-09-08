package com.levioza.velox.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import velox_cmp.design_system.velox.generated.resources.Res
import velox_cmp.design_system.velox.generated.resources.readex_pro
import org.jetbrains.compose.resources.Font

/**
 * Returns the bundled Readex Pro family used for both Latin and Arabic Velox text.
 */
@Composable
fun ReadexProFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.readex_pro, weight = FontWeight.ExtraLight),
        Font(Res.font.readex_pro, weight = FontWeight.Light),
        Font(Res.font.readex_pro, weight = FontWeight.Normal),
        Font(Res.font.readex_pro, weight = FontWeight.Medium),
        Font(Res.font.readex_pro, weight = FontWeight.SemiBold),
        Font(Res.font.readex_pro, weight = FontWeight.Bold),
    )

/**
 * Returns Velox's Material 3 type scale using Readex Pro for English and Arabic.
 *
 * Every style is derived from the Material default rather than built from scratch, so it keeps the
 * line-height and platform metrics Material sets. A style built from a bare [TextStyle] measures a
 * line from the glyphs it happens to contain, which makes a text field change height the moment
 * the first character is typed.
 */
@Composable
fun VeloxTypography(): Typography {
    val fontFamily = ReadexProFontFamily()
    return remember(fontFamily) {
        val defaults = Typography()
        Typography(
            displayLarge = defaults.displayLarge.withFont(fontFamily),
            displayMedium = defaults.displayMedium.withFont(fontFamily),
            displaySmall =
                defaults.displaySmall.copy(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    lineHeight = 44.sp,
                ),
            headlineLarge = defaults.headlineLarge.withFont(fontFamily),
            headlineMedium =
                defaults.headlineMedium.copy(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    lineHeight = 36.sp,
                ),
            headlineSmall = defaults.headlineSmall.withFont(fontFamily),
            titleLarge =
                defaults.titleLarge.copy(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                ),
            titleMedium = defaults.titleMedium.withFont(fontFamily),
            titleSmall = defaults.titleSmall.withFont(fontFamily),
            bodyLarge =
                defaults.bodyLarge.copy(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                ),
            bodyMedium = defaults.bodyMedium.withFont(fontFamily),
            bodySmall = defaults.bodySmall.withFont(fontFamily),
            labelLarge =
                defaults.labelLarge.copy(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
            labelMedium = defaults.labelMedium.withFont(fontFamily),
            labelSmall = defaults.labelSmall.withFont(fontFamily),
        )
    }
}

private fun TextStyle.withFont(fontFamily: FontFamily): TextStyle = copy(fontFamily = fontFamily)
