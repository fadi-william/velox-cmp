package com.levioza.velox.components.navigation

import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Tab as MaterialTab

/**
 * Defines the supported tabs variant values.
 */
enum class TabsVariant { Primary, Secondary }

/**
 * Renders the Velox tabs component.
 *
 * @param selectedIndex Index of the currently selected item.
 * @param onSelected Callback invoked when the selection changes.
 * @param labels Value used to configure the component.
 * @param modifier Modifier applied to the component.
 * @param variant Visual variant of the component.
 */
@Composable
fun Tabs(
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    labels: List<String>,
    modifier: Modifier = Modifier,
    variant: TabsVariant = TabsVariant.Primary,
) {
    val tabs: @Composable () -> Unit = {
        labels.forEachIndexed { index, label ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onSelected(index) },
                text = label,
            )
        }
    }
    when (variant) {
        TabsVariant.Primary -> PrimaryTabRow(
            selectedTabIndex = selectedIndex.coerceIn(0, (labels.size - 1).coerceAtLeast(0)),
            modifier = modifier,
            tabs = tabs,
        )
        TabsVariant.Secondary -> SecondaryTabRow(
            selectedTabIndex = selectedIndex.coerceIn(0, (labels.size - 1).coerceAtLeast(0)),
            modifier = modifier,
            tabs = tabs,
        )
    }
}

/**
 * Renders the Velox tab component.
 *
 * @param selected The currently selected value.
 * @param onClick Callback invoked when the component is activated.
 * @param text Text displayed by the component.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param icon Icon content displayed by the component.
 */
@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
) {
    MaterialTab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = { Text(text) },
        icon = icon,
    )
}
