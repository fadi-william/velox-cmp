@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.surfaces.SearchBar
import org.jetbrains.compose.storytale.story

val `SearchBar` by
    story(group = "Surfaces & Search") {
        val placeholder by parameter("Search components")
        val enabled by parameter(true)
        val showLeadingIcon by parameter(true)

        var query by remember { mutableStateOf("") }
        SearchBar(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.width(360.dp),
            placeholder = placeholder,
            enabled = enabled,
            leadingIcon =
                if (showLeadingIcon) {
                    {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            Modifier.size(24.dp),
                        )
                    }
                } else {
                    null
                },
        )
    }
