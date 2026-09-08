@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.containment

import com.levioza.velox.components.containment.Tooltip
import com.levioza.velox.components.inputs.basic.Button
import org.jetbrains.compose.storytale.story

val `Tooltip` by
    story(group = "Containment") {
        val text by parameter("Adds this item to favorites")
        val anchorLabel by parameter("Hover or focus me")

        Tooltip(text) { Button(anchorLabel, {}) }
    }
