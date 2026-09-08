package com.levioza.velox.components.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.foundation.Badge
import androidx.compose.material3.NavigationDrawerItem as MaterialNavigationDrawerItem

/**
 * Defines the supported navigation drawer variant values.
 */
enum class NavigationDrawerVariant { Modal, Standard }

/**
 * Renders the Velox navigation drawer component.
 *
 * @param drawerState Value used to configure the component.
 * @param modifier Modifier applied to the component.
 * @param variant Visual variant of the component.
 * @param drawerContent Content rendered inside the navigation drawer.
 * @param content Content rendered inside the component.
 */
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    variant: NavigationDrawerVariant = NavigationDrawerVariant.Modal,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    when (variant) {
        NavigationDrawerVariant.Modal -> ModalNavigationDrawer(
            drawerContent = { ModalDrawerSheet(content = drawerContent) },
            modifier = modifier,
            drawerState = drawerState,
            content = content,
        )
        NavigationDrawerVariant.Standard -> PermanentNavigationDrawer(
            drawerContent = { PermanentDrawerSheet(content = drawerContent) },
            modifier = modifier,
            content = content,
        )
    }
}

/**
 * Renders the Velox navigation drawer item component.
 *
 * @param label Label text or label provider for the component.
 * @param selected The currently selected value.
 * @param onClick Callback invoked when the component is activated.
 * @param modifier Modifier applied to the component.
 * @param icon Icon content displayed by the component.
 * @param badge Optional badge text associated with the destination.
 */
@Composable
fun NavigationDrawerItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    badge: String? = null,
) {
    MaterialNavigationDrawerItem(
        label = { Text(label) },
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        badge = badge?.let { value -> { Badge(text = value) } },
    )
}

/**
 * Renders the Velox navigation drawer headline component.
 *
 * @param text Text displayed by the component.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun NavigationDrawerHeadline(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
