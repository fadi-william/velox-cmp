@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.pickers

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.pickers.TimeField
import org.jetbrains.compose.storytale.story

val `TimeField` by
    story(group = "Pickers") {
        val label by parameter("Time")
        val placeholder by parameter("HH:MM")
        val supportingText by parameter("")
        val initialValue by parameter("10:30")
        val enabled by parameter(true)
        val isError by parameter(false)
        val is24Hour by parameter(false)

        var value by remember(initialValue) { mutableStateOf(initialValue) }
        TimeField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(360.dp),
            label = label,
            placeholder = placeholder,
            enabled = enabled,
            isError = isError,
            supportingText = supportingText.takeIf { it.isNotBlank() },
            is24Hour = is24Hour,
        )
    }
