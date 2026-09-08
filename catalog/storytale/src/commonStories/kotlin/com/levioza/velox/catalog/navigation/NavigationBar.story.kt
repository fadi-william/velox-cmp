@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.foundation.Badge
import com.levioza.velox.components.navigation.NavigationBar
import com.levioza.velox.components.navigation.NavigationItem
import org.jetbrains.compose.storytale.story

private val NavigationIcons =
    listOf(Icons.Default.Home, Icons.Default.Search, Icons.Default.Person, Icons.Default.Settings)

val `NavigationBar` by
    story(group = "Navigation") {
        val labels by parameter("Home, Search, Profile")
        val badgeOnSecondItem by parameter("")

        val items =
            labels.split(",").map { it.trim() }.filter { it.isNotEmpty() }.mapIndexed { index, label
                ->
                NavigationItem(
                    value = label.lowercase(),
                    label = label,
                    icon = NavigationIcons[index % NavigationIcons.size],
                    badge =
                        if (badgeOnSecondItem.isNotBlank() && index == 1) {
                            { Badge(text = badgeOnSecondItem) }
                        } else {
                            null
                        },
                )
            }
        if (items.isNotEmpty()) {
            var selected by remember(items) { mutableStateOf(items.first().value) }
            NavigationBar(items, selected, { selected = it })
        }
    }
