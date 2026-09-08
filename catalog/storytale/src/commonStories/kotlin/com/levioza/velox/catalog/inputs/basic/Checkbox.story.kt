@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.basic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.inputs.basic.Checkbox
import com.levioza.velox.components.inputs.basic.CheckboxState
import org.jetbrains.compose.storytale.story

val `Checkbox` by
    story(group = "Basic Inputs") {
        val label by parameter("Receive updates")
        val initialState by parameter(CheckboxState.Checked)
        val enabled by parameter(true)

        var state by remember(initialState) { mutableStateOf(initialState) }
        Checkbox(
            state = state,
            onStateChange = { state = it },
            enabled = enabled,
            label = label,
        )
    }
