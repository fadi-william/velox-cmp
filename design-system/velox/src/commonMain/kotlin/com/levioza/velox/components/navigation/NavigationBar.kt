package com.levioza.velox.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.NavigationBar as MaterialNavigationBar

/**
 * Describes a destination displayed by navigation components.
 *
 * @param value Current controlled value.
 * @param label Label text or label provider for the component.
 * @param icon Icon content displayed by the component.
 * @param selectedIcon Value used to configure the component.
 * @param enabled Whether user interaction is enabled.
 * @param badge Optional badge text associated with the destination.
 */
data class NavigationItem<T>(
    val value: T,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
    val enabled: Boolean = true,
    val badge: (@Composable () -> Unit)? = null,
)

/**
 * Renders the Velox navigation bar component.
 *
 * @param items Items rendered by the component.
 * @param selected The currently selected value.
 * @param onSelected Callback invoked when the selection changes.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun <T> NavigationBar(
    items: List<NavigationItem<T>>,
    selected: T,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    MaterialNavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selected == item.value,
                onClick = { onSelected(item.value) },
                enabled = item.enabled,
                icon = {
                    androidx.compose.material3.BadgedBox(
                        badge = { item.badge?.invoke() },
                    ) {
                        Icon(
                            imageVector = if (selected == item.value) item.selectedIcon else item.icon,
                            contentDescription = item.label,
                        )
                    }
                },
                label = { Text(item.label) },
            )
        }
    }
}
