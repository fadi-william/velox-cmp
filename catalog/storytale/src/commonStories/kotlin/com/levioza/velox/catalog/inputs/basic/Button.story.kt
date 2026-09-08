@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.basic

import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.inputs.basic.ButtonSize
import com.levioza.velox.components.inputs.basic.ButtonVariant
import org.jetbrains.compose.storytale.story

val `Button` by
    story(group = "Basic Inputs") {
        val text by parameter("Filled")
        val variant by parameter(ButtonVariant.Filled)
        val size by parameter(ButtonSize.Medium)
        val enabled by parameter(true)
        val loading by parameter(false)

        Button(
            text = text,
            onClick = {},
            enabled = enabled,
            variant = variant,
            size = size,
            loading = loading,
        )
    }
