@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.actions

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Defines the supported fabvariant values.
 */
enum class FABVariant { Primary, Secondary, Tertiary, Surface }
/**
 * Defines the supported fabsize values.
 */
enum class FABSize { Small, Medium, Large }

/**
 * Renders a Material 3 floating action button.
 *
 * @param onClick Callback invoked when the component is activated.
 * @param icon Icon content displayed by the component.
 * @param modifier Modifier applied to the component.
 * @param variant Visual variant of the component.
 * @param size Visual size of the component.
 */
@Composable
fun FAB(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    variant: FABVariant = FABVariant.Primary,
    size: FABSize = FABSize.Medium,
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
    when (size) {
        FABSize.Small -> SmallFloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            content = icon,
        )
        FABSize.Medium -> FloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            content = icon,
        )
        FABSize.Large -> LargeFloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            content = icon,
        )
    }
}
