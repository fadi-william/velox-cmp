@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.basic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.inputs.basic.RadioGroup
import org.jetbrains.compose.storytale.story

val `RadioButton` by
    story(group = "Basic Inputs") {
        val options by parameter("Daily, Weekly, Never")
        val initialSelection by parameter(0)

        val choices = options.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        var selected by
            remember(choices, initialSelection) {
                mutableStateOf(choices.getOrNull(initialSelection))
            }
        RadioGroup(
            options = choices,
            selected = selected,
            onSelected = { selected = it },
            label = { it },
        )
    }
