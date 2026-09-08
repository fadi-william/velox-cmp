package com.levioza.velox.components.inputs.basic

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.material3.IconButton as MaterialIconButton

/**
 * Defines the supported icon button variant values.
 */
enum class IconButtonVariant { Standard, Filled, Tonal, Outlined }
/**
 * Defines the supported icon button size values.
 */
enum class IconButtonSize { Small, Medium, Large }

/**
 * Renders the Velox icon button component.
 *
 * @param icon Icon content displayed by the component.
 * @param contentDescription Accessible description of the icon.
 * @param onClick Callback invoked when the component is activated.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param variant Visual variant of the component.
 * @param size Visual size of the component.
 */
@Composable
fun IconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: IconButtonVariant = IconButtonVariant.Standard,
    size: IconButtonSize = IconButtonSize.Medium,
) {
    val buttonSize = when (size) {
        IconButtonSize.Small -> 40.dp
        IconButtonSize.Medium -> 48.dp
        IconButtonSize.Large -> 56.dp
    }
    val content: @Composable () -> Unit = {
        androidx.compose.material3.Icon(icon, contentDescription = contentDescription)
    }
    val sizedModifier = modifier.size(buttonSize)
    when (variant) {
        IconButtonVariant.Standard -> MaterialIconButton(onClick, sizedModifier, enabled, content = content)
        IconButtonVariant.Filled -> FilledIconButton(onClick, sizedModifier, enabled, content = content)
        IconButtonVariant.Tonal -> FilledTonalIconButton(onClick, sizedModifier, enabled, content = content)
        IconButtonVariant.Outlined -> OutlinedIconButton(onClick, sizedModifier, enabled, content = content)
    }
}
