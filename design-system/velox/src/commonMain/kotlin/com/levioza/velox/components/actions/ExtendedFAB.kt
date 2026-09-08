@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.actions

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Renders a Material 3 extended floating action button.
 *
 * @param text Text displayed by the component.
 * @param onClick Callback invoked when the component is activated.
 * @param modifier Modifier applied to the component.
 * @param icon Icon content displayed by the component.
 * @param variant Visual variant of the component.
 * @param expanded Whether the component is currently expanded.
 */
@Composable
fun ExtendedFAB(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    variant: FABVariant = FABVariant.Primary,
    expanded: Boolean = true,
) {
    val containerColor = when (variant) {
        FABVariant.Primary -> MaterialTheme.colorScheme.primaryContainer
        FABVariant.Secondary -> MaterialTheme.colorScheme.secondaryContainer
        FABVariant.Tertiary -> MaterialTheme.colorScheme.tertiaryContainer
        FABVariant.Surface -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val contentColor = when (variant) {
        FABVariant.Primary -> MaterialTheme.colorScheme.onPrimaryContainer
        FABVariant.Secondary -> MaterialTheme.colorScheme.onSecondaryContainer
        FABVariant.Tertiary -> MaterialTheme.colorScheme.onTertiaryContainer
        FABVariant.Surface -> MaterialTheme.colorScheme.primary
    }
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        expanded = expanded,
        icon = { icon?.invoke() },
        text = { Text(text) },
        containerColor = containerColor,
        contentColor = contentColor,
    )
}
