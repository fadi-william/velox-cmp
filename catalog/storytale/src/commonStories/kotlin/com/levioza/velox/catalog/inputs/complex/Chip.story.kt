@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.complex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.inputs.complex.Chip
import com.levioza.velox.components.inputs.complex.ChipVariant
import org.jetbrains.compose.storytale.story

val `Chip` by
    story(group = "Complex Inputs") {
        val label by parameter("Filter")
        val variant by parameter(ChipVariant.Filter)
        val enabled by parameter(true)

        var selected by remember { mutableStateOf(false) }
        Chip(
            label = label,
            onClick = { selected = !selected },
            enabled = enabled,
            variant = variant,
            selected = selected,
        )
    }
