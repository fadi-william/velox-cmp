@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.containment

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.DropdownMenu as MaterialDropdownMenu
import androidx.compose.material3.DropdownMenuItem as MaterialDropdownMenuItem

/**
 * Renders the Velox menu component.
 *
 * @param expanded Whether the component is currently expanded.
 * @param onDismissRequest Callback invoked when dismissal is requested.
 * @param modifier Modifier applied to the component.
 * @param content Content rendered inside the component.
 */
@Composable
fun Menu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    MaterialDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        content = content,
    )
}

/**
 * Renders the Velox menu item component.
 *
 * @param text Text displayed by the component.
 * @param onClick Callback invoked when the component is activated.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param leadingIcon Optional icon displayed before the main content.
 * @param trailingIcon Optional icon displayed after the main content.
 */
@Composable
fun MenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    MaterialDropdownMenuItem(
        text = { Text(text) },
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
    )
}

/**
 * Renders the Velox menu divider component.
 */
@Composable
fun MenuDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier)
}
