@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.navigation.NavigationItem
import com.levioza.velox.components.navigation.NavigationRail
import com.levioza.velox.components.navigation.NavigationRailAlignment
import org.jetbrains.compose.storytale.story

private val RailIcons =
    listOf(Icons.Default.Home, Icons.Default.Search, Icons.Default.Person, Icons.Default.Settings)

val `NavigationRail` by
    story(group = "Navigation") {
        val labels by parameter("Home, Search")
        val alignment by parameter(NavigationRailAlignment.Top)

        val items =
            labels.split(",").map { it.trim() }.filter { it.isNotEmpty() }.mapIndexed { index, label
                ->
                NavigationItem(
                    value = label.lowercase(),
                    label = label,
                    icon = RailIcons[index % RailIcons.size],
                )
            }
        if (items.isNotEmpty()) {
            var selected by remember(items) { mutableStateOf(items.first().value) }
            NavigationRail(
                items,
                selected,
                { selected = it },
                modifier = Modifier.height(320.dp),
                alignment = alignment,
            )
        }
    }
