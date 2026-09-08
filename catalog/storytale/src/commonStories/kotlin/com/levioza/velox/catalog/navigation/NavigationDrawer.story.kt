@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.navigation.NavigationDrawer
import com.levioza.velox.components.navigation.NavigationDrawerItem
import com.levioza.velox.components.navigation.NavigationDrawerVariant
import org.jetbrains.compose.storytale.story

private val DrawerIcons =
    listOf(
        Icons.Default.Home,
        Icons.Default.Inbox,
        Icons.AutoMirrored.Filled.Send,
        Icons.Default.Favorite,
        Icons.Default.Delete,
    )

val `NavigationDrawer` by
    story(group = "Navigation") {
        val labels by parameter("Home, Inbox, Sent, Favorites, Trash")
        val variant by parameter(NavigationDrawerVariant.Standard)
        val badgeOnInbox by parameter("24")

        val items = labels.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        var selected by remember(items) { mutableStateOf(items.firstOrNull().orEmpty()) }

        NavigationDrawer(
            drawerState = drawerState,
            variant = variant,
            drawerContent = {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Spacer(Modifier.height(12.dp))
                    items.forEachIndexed { index, label ->
                        NavigationDrawerItem(
                            label = label,
                            selected = selected == label,
                            onClick = { selected = label },
                            icon = {
                                Icon(
                                    DrawerIcons[index % DrawerIcons.size],
                                    contentDescription = null,
                                )
                            },
                            badge = badgeOnInbox.takeIf { it.isNotBlank() && index == 1 },
                        )
                    }
                }
            },
        ) {
            Text("Selected: $selected", Modifier.padding(24.dp))
        }
    }
