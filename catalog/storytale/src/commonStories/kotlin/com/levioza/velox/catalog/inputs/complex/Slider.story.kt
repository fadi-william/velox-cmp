@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.complex

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.Slider
import org.jetbrains.compose.storytale.story

val `Slider` by
    story(group = "Complex Inputs") {
        val initialValue by parameter(0.45f)
        val steps by parameter(0)
        val enabled by parameter(true)

        var value by remember(initialValue) { mutableFloatStateOf(initialValue) }
        Slider(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(320.dp),
            enabled = enabled,
            steps = steps,
        )
    }
