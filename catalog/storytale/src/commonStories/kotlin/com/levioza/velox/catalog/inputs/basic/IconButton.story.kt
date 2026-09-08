@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.basic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import com.levioza.velox.components.inputs.basic.IconButton
import com.levioza.velox.components.inputs.basic.IconButtonSize
import com.levioza.velox.components.inputs.basic.IconButtonVariant
import org.jetbrains.compose.storytale.story

val `IconButton` by
    story(group = "Basic Inputs") {
        val variant by parameter(IconButtonVariant.Standard)
        val size by parameter(IconButtonSize.Medium)
        val enabled by parameter(true)

        IconButton(
            icon = Icons.Default.Favorite,
            contentDescription = "Favorite",
            onClick = {},
            enabled = enabled,
            variant = variant,
            size = size,
        )
    }
