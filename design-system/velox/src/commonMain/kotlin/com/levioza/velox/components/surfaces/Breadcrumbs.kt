package com.levioza.velox.components.surfaces

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Describes an item in a breadcrumb trail.
 *
 * @param label Label text or label provider for the component.
 * @param onClick Callback invoked when the component is activated.
 */
data class BreadcrumbItem(
    val label: String,
    val onClick: (() -> Unit)? = null,
)

/**
 * Renders the Velox breadcrumbs component.
 *
 * @param items Items rendered by the component.
 * @param modifier Modifier applied to the component.
 * @param separator Text placed between breadcrumb items.
 */
@Composable
fun Breadcrumbs(
    items: List<BreadcrumbItem>,
    modifier: Modifier = Modifier,
    separator: String = "/",
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items.forEachIndexed { index, item ->
            if (index > 0) {
                Text(separator, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (item.onClick != null && index != items.lastIndex) {
                androidx.compose.material3.TextButton(onClick = item.onClick) {
                    Text(item.label)
                }
            } else {
                Text(
                    item.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (index == items.lastIndex) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }
    }
}
