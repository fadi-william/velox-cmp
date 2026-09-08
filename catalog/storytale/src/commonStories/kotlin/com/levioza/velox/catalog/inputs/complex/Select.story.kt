@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.complex

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.Select
import com.levioza.velox.components.inputs.complex.SelectOption
import org.jetbrains.compose.storytale.story

val `Select` by
    story(group = "Complex Inputs") {
        val label by parameter("Team")
        val placeholder by parameter("Select an option")
        val optionLabels by parameter("Design, Engineering, Research")
        val enabled by parameter(true)
        val isError by parameter(false)
        val supportingText by parameter("")

        val options =
            optionLabels.split(",").map { it.trim() }.filter { it.isNotEmpty() }.map {
                SelectOption(it, it)
            }
        var selected by remember { mutableStateOf<String?>(null) }
        var expanded by remember { mutableStateOf(false) }
        Select(
            options = options,
            selected = selected,
            onSelected = { selected = it },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.width(360.dp),
            label = label,
            placeholder = placeholder,
            enabled = enabled,
            isError = isError,
            supportingText = supportingText.takeIf { it.isNotBlank() },
        )
    }
