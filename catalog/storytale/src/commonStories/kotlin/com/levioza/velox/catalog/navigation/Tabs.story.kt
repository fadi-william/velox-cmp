@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.navigation.Tabs
import com.levioza.velox.components.navigation.TabsVariant
import org.jetbrains.compose.storytale.story

val `Tabs` by
    story(group = "Navigation") {
        val titles by parameter("Overview, Specs, Usage")
        val variant by parameter(TabsVariant.Primary)

        val tabs = titles.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        if (tabs.isNotEmpty()) {
            var selected by remember(tabs) { mutableIntStateOf(0) }
            Tabs(
                selectedIndex = selected.coerceIn(0, tabs.lastIndex),
                onSelected = { selected = it },
                labels = tabs,
                variant = variant,
            )
        }
    }
