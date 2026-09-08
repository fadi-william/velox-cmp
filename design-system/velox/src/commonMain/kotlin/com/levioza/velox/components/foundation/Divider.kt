package com.levioza.velox.components.foundation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Defines the supported divider variant values.
 */
enum class DividerVariant { Full, Inset, Middle }
/**
 * Defines the supported divider orientation values.
 */
enum class DividerOrientation { Horizontal, Vertical }

/**
 * Renders the Velox divider component.
 *
 * @param modifier Modifier applied to the component.
 * @param variant Visual variant of the component.
 * @param orientation Orientation in which the component is rendered.
 */
@Composable
fun Divider(
    modifier: Modifier = Modifier,
    variant: DividerVariant = DividerVariant.Full,
    orientation: DividerOrientation = DividerOrientation.Horizontal,
) {
    val insetModifier = when (variant) {
        DividerVariant.Full -> modifier
        DividerVariant.Inset -> modifier.padding(start = 16.dp)
        DividerVariant.Middle -> modifier.padding(horizontal = 16.dp)
    }
    when (orientation) {
        DividerOrientation.Horizontal -> HorizontalDivider(modifier = insetModifier)
        DividerOrientation.Vertical -> VerticalDivider(modifier = insetModifier)
    }
}
