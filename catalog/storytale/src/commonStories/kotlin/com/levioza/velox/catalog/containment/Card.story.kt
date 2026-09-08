@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.containment

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.containment.Card
import com.levioza.velox.components.containment.CardVariant
import org.jetbrains.compose.storytale.story

val `Card` by
    story(group = "Containment") {
        val text by parameter("Card content")
        val variant by parameter(CardVariant.Filled)
        val clickable by parameter(false)

        Card(
            modifier = Modifier.width(220.dp),
            variant = variant,
            onClick = if (clickable) ({}) else null,
        ) {
            Text(text, Modifier.padding(20.dp))
        }
    }
