@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.containment

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.containment.Menu
import com.levioza.velox.components.containment.MenuDivider
import com.levioza.velox.components.containment.MenuItem
import com.levioza.velox.components.inputs.basic.Button
import org.jetbrains.compose.storytale.story

val `Menu` by
    story(group = "Containment") {
        val itemLabels by parameter("Edit, Archive, Delete")
        val dividerBeforeLast by parameter(true)

        val items = itemLabels.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        var expanded by remember { mutableStateOf(false) }
        Box {
            Button("Open menu", { expanded = true })
            Menu(expanded, { expanded = false }) {
                items.forEachIndexed { index, label ->
                    if (dividerBeforeLast && index == items.lastIndex && index > 0) MenuDivider()
                    MenuItem(label, { expanded = false })
                }
            }
        }
    }
