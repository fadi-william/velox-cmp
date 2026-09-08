@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.actions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.actions.SegmentedButton
import com.levioza.velox.components.actions.SegmentedButtonOption
import org.jetbrains.compose.storytale.story

val `SegmentedButton` by
    story(group = "Actions") {
        val optionLabels by parameter("Day, Week, Month")
        val enabled by parameter(true)

        val options =
            optionLabels.split(",").map { it.trim() }.filter { it.isNotEmpty() }.map {
                SegmentedButtonOption(it, it, enabled = enabled)
            }
        // An emptied option list has nothing to render, so the component is left out entirely.
        if (options.isNotEmpty()) {
            var selected by remember(options) { mutableStateOf(options.first().value) }
            SegmentedButton(options, selected, { selected = it })
        }
    }
