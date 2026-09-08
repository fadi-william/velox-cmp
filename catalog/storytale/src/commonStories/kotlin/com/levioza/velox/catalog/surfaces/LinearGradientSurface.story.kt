@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.surfaces.LinearGradientSurface
import org.jetbrains.compose.storytale.story

val `LinearGradientSurface` by
    story(group = "Surfaces & Search") {
        val text by parameter("Velox gradient surface")
        val cornerRadius by parameter(24)
        val useTertiary by parameter(true)

        LinearGradientSurface(
            colors =
                listOf(
                    MaterialTheme.colorScheme.primaryContainer,
                    if (useTertiary) {
                        MaterialTheme.colorScheme.tertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    },
                ),
            modifier = Modifier.size(360.dp, 180.dp),
            shape = RoundedCornerShape(cornerRadius.dp),
        ) {
            Text(text, Modifier.align(Alignment.Center))
        }
    }
