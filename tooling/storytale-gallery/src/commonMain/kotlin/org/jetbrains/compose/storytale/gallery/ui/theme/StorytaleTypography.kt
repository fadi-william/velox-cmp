package org.jetbrains.compose.storytale.gallery.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.storytale.gallery.generated.resources.Res
import org.jetbrains.compose.storytale.gallery.generated.resources.readex_pro

/** Returns Storytale's Material type scale using Readex Pro for Latin and Arabic text. */
@Composable
internal fun StorytaleTypography(): Typography {
    val fontFamily =
        FontFamily(
            Font(Res.font.readex_pro, weight = FontWeight.ExtraLight),
            Font(Res.font.readex_pro, weight = FontWeight.Light),
            Font(Res.font.readex_pro, weight = FontWeight.Normal),
            Font(Res.font.readex_pro, weight = FontWeight.Medium),
            Font(Res.font.readex_pro, weight = FontWeight.SemiBold),
            Font(Res.font.readex_pro, weight = FontWeight.Bold),
        )

    return remember(fontFamily) {
        val defaults = Typography()
        Typography(
            displayLarge = defaults.displayLarge.withFont(fontFamily),
            displayMedium = defaults.displayMedium.withFont(fontFamily),
            displaySmall = defaults.displaySmall.withFont(fontFamily),
            headlineLarge = defaults.headlineLarge.withFont(fontFamily),
            headlineMedium = defaults.headlineMedium.withFont(fontFamily),
            headlineSmall = defaults.headlineSmall.withFont(fontFamily),
            titleLarge = defaults.titleLarge.withFont(fontFamily),
            titleMedium = defaults.titleMedium.withFont(fontFamily),
            titleSmall = defaults.titleSmall.withFont(fontFamily),
            bodyLarge = defaults.bodyLarge.withFont(fontFamily),
            bodyMedium = defaults.bodyMedium.withFont(fontFamily),
            bodySmall = defaults.bodySmall.withFont(fontFamily),
            labelLarge = defaults.labelLarge.withFont(fontFamily),
            labelMedium = defaults.labelMedium.withFont(fontFamily),
            labelSmall = defaults.labelSmall.withFont(fontFamily),
        )
    }
}

private fun TextStyle.withFont(fontFamily: FontFamily): TextStyle = copy(fontFamily = fontFamily)
