package com.levioza.velox.components.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.NavigationRail as MaterialNavigationRail

/**
 * Defines the supported navigation rail alignment values.
 */
enum class NavigationRailAlignment { Top, Center }

/**
 * Renders the Velox navigation rail component.
 *
 * @param items Items rendered by the component.
 * @param selected The currently selected value.
 * @param onSelected Callback invoked when the selection changes.
 * @param modifier Modifier applied to the component.
 * @param alignment Vertical alignment of the rail content.
 * @param header Header content displayed by the component.
 */
@Composable
fun <T> NavigationRail(
    items: List<NavigationItem<T>>,
    selected: T,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    alignment: NavigationRailAlignment = NavigationRailAlignment.Top,
    header: (@Composable ColumnScope.() -> Unit)? = null,
) {
    MaterialNavigationRail(
        modifier = modifier,
        header = header,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        if (alignment == NavigationRailAlignment.Center) {
            androidx.compose.foundation.layout.Spacer(Modifier.weight(1f))
        }
        items.forEach { item ->
            NavigationRailItem(
                selected = selected == item.value,
                onClick = { onSelected(item.value) },
                enabled = item.enabled,
                icon = {
                    Icon(
                        imageVector = if (selected == item.value) item.selectedIcon else item.icon,
                        contentDescription = item.label,
                    )
                },
                label = { Text(item.label) },
            )
        }
        if (alignment == NavigationRailAlignment.Center) {
            androidx.compose.foundation.layout.Spacer(Modifier.weight(1f))
        }
    }
}
