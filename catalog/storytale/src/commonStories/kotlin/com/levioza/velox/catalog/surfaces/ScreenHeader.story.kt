@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.inputs.basic.ButtonSize
import com.levioza.velox.components.inputs.basic.IconButton
import com.levioza.velox.components.surfaces.ScreenHeader
import org.jetbrains.compose.storytale.story

val `ScreenHeader` by
    story(group = "Surfaces & Search") {
        val title by parameter("Components")
        val subtitle by parameter("Explore the Velox design system")
        val showNavigation by parameter(true)
        val actionLabel by parameter("Add")

        ScreenHeader(
            title = title,
            subtitle = subtitle.takeIf { it.isNotBlank() },
            navigation =
                if (showNavigation) {
                    { IconButton(Icons.AutoMirrored.Filled.ArrowBack, "Back", onClick = {}) }
                } else {
                    null
                },
            actions = {
                if (actionLabel.isNotBlank()) {
                    Button(actionLabel, {}, size = ButtonSize.Small)
                }
            },
        )
    }
