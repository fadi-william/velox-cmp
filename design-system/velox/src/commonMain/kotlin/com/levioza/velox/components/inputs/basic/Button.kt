package com.levioza.velox.components.inputs.basic

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button as MaterialButton
import androidx.compose.material3.ElevatedButton as MaterialElevatedButton
import androidx.compose.material3.FilledTonalButton as MaterialFilledTonalButton
import androidx.compose.material3.OutlinedButton as MaterialOutlinedButton
import androidx.compose.material3.TextButton as MaterialTextButton

/**
 * Defines the supported button variant values.
 */
enum class ButtonVariant {
    Filled,
    Outlined,
    Text,
    Elevated,
    Tonal,
}

/**
 * Defines the supported button size values.
 */
enum class ButtonSize {
    Small,
    Medium,
    Large,
}

/**
 * Velox's Material 3 action button.
 *
 * The API mirrors the web component's variants while delegating interaction, accessibility,
 * theming, and platform behavior to Compose Material 3.
 */
@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.Filled,
    size: ButtonSize = ButtonSize.Medium,
    loading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val minHeight = when (size) {
        ButtonSize.Small -> 40.dp
        ButtonSize.Medium -> 48.dp
        ButtonSize.Large -> 56.dp
    }
    val horizontalPadding = when (size) {
        ButtonSize.Small -> 16.dp
        ButtonSize.Medium -> 20.dp
        ButtonSize.Large -> 24.dp
    }
    val textStyle = when (size) {
        ButtonSize.Small -> MaterialTheme.typography.labelMedium
        ButtonSize.Medium -> MaterialTheme.typography.labelLarge
        ButtonSize.Large -> MaterialTheme.typography.titleSmall
    }
    val contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 8.dp)
    val buttonModifier = modifier.defaultMinSize(minHeight = minHeight)
    val content: @Composable RowScope.() -> Unit = {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = androidx.compose.material3.LocalContentColor.current,
            )
        } else {
            leadingIcon?.invoke()
            Text(text = text, style = textStyle)
            trailingIcon?.invoke()
        }
    }

    when (variant) {
        ButtonVariant.Filled -> MaterialButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled && !loading,
            contentPadding = contentPadding,
            content = content,
        )
        ButtonVariant.Outlined -> MaterialOutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled && !loading,
            contentPadding = contentPadding,
            content = content,
        )
        ButtonVariant.Text -> MaterialTextButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled && !loading,
            contentPadding = contentPadding,
            content = content,
        )
        ButtonVariant.Elevated -> MaterialElevatedButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled && !loading,
            contentPadding = contentPadding,
            content = content,
        )
        ButtonVariant.Tonal -> MaterialFilledTonalButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = enabled && !loading,
            contentPadding = contentPadding,
            content = content,
        )
    }
}
