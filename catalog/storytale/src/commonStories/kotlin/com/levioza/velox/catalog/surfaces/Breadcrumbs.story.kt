@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import com.levioza.velox.components.surfaces.BreadcrumbItem
import com.levioza.velox.components.surfaces.Breadcrumbs
import org.jetbrains.compose.storytale.story

val `Breadcrumbs` by
    story(group = "Surfaces & Search") {
        val path by parameter("Home, Components, Button")

        val labels = path.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        if (labels.isEmpty()) return@story
        Breadcrumbs(
            labels.mapIndexed { index, label ->
                // The last crumb is the current page, so it carries no click handler.
                BreadcrumbItem(label, if (index == labels.lastIndex) null else ({}))
            },
        )
    }
