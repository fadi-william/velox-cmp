@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.basic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.inputs.basic.Switch
import org.jetbrains.compose.storytale.story

val `Switch` by
    story(group = "Basic Inputs") {
        val label by parameter("Notifications")
        val initialChecked by parameter(true)
        val enabled by parameter(true)

        var checked by remember(initialChecked) { mutableStateOf(initialChecked) }
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            enabled = enabled,
            label = label,
        )
    }
