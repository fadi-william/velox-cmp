@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.containment.Card
import com.levioza.velox.components.surfaces.HorizontalScrollView
import org.jetbrains.compose.storytale.story

val `HorizontalScrollView` by
    story(group = "Surfaces & Search") {
        val cardCount by parameter(5)
        val cardWidth by parameter(150)
        val spacing by parameter(12)

        HorizontalScrollView(
            modifier = Modifier.width(420.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp),
        ) {
            repeat(cardCount) {
                Card(Modifier.width(cardWidth.dp)) {
                    Text("Card ${it + 1}", Modifier.padding(24.dp))
                }
            }
        }
    }
