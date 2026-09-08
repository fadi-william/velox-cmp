@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.containment.Card
import com.levioza.velox.components.surfaces.CardGrid
import org.jetbrains.compose.storytale.story

val `CardGrid` by
    story(group = "Surfaces & Search") {
        val cardCount by parameter(6)
        val minimumCardWidth by parameter(180)
        val spacing by parameter(16)

        CardGrid(
            modifier = Modifier.width(600.dp).height(360.dp),
            minimumCardWidth = minimumCardWidth.dp,
            horizontalSpacing = spacing.dp,
            verticalSpacing = spacing.dp,
        ) {
            items(cardCount) { index -> Card { Text("Card ${index + 1}", Modifier.padding(24.dp)) } }
        }
    }
