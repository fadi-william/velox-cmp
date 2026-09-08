@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.complex

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.Autocomplete
import com.levioza.velox.components.inputs.complex.SelectOption
import org.jetbrains.compose.storytale.story

val `Autocomplete` by
    story(group = "Complex Inputs") {
        val label by parameter("City")
        val placeholder by parameter("Search")
        val optionLabels by parameter("Cairo, London, Tokyo, Toronto, Lisbon")
        val enabled by parameter(true)
        val isError by parameter(false)
        val supportingText by parameter("")

        val options =
            optionLabels.split(",").map { it.trim() }.filter { it.isNotEmpty() }.map {
                SelectOption(it, it)
            }
        var value by remember { mutableStateOf("") }
        Autocomplete(
            value = value,
            onValueChange = { value = it },
            options = options,
            onSelected = { value = it.orEmpty() },
            modifier = Modifier.width(360.dp),
            label = label,
            placeholder = placeholder,
            enabled = enabled,
            isError = isError,
            supportingText = supportingText.takeIf { it.isNotBlank() },
        )
    }
