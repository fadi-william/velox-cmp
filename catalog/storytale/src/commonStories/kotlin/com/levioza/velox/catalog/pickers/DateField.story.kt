@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.pickers

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.pickers.DateField
import org.jetbrains.compose.storytale.story

val `DateField` by
    story(group = "Pickers") {
        val label by parameter("Date")
        val placeholder by parameter("YYYY-MM-DD")
        val supportingText by parameter("")
        val initialValue by parameter("2026-09-08")
        val enabled by parameter(true)
        val isError by parameter(false)

        var value by remember(initialValue) { mutableStateOf(initialValue) }
        DateField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(360.dp),
            label = label,
            placeholder = placeholder,
            enabled = enabled,
            isError = isError,
            supportingText = supportingText.takeIf { it.isNotBlank() },
        )
    }
