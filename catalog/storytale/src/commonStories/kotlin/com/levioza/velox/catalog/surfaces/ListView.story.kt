@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.containment.ListItem
import com.levioza.velox.components.surfaces.ListView
import org.jetbrains.compose.storytale.story

val `ListView` by
    story(group = "Surfaces & Search") {
        val itemCount by parameter(12)
        val supportingText by parameter("Material 3 component")
        val verticalSpacing by parameter(8)

        ListView(
            items = List(itemCount) { "Item ${it + 1}" },
            modifier = Modifier.width(360.dp).height(280.dp),
            verticalSpacing = verticalSpacing.dp,
        ) {
            ListItem(it, supportingText = supportingText.takeIf { text -> text.isNotBlank() })
        }
    }
