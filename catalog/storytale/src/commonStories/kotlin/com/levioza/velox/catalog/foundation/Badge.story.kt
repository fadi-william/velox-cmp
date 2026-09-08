@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.foundation

import com.levioza.velox.components.foundation.Badge
import com.levioza.velox.components.foundation.BadgeSize
import org.jetbrains.compose.storytale.story

val `Badge` by
    story(group = "Foundation") {
        val text by parameter("8")
        val size by parameter(BadgeSize.Large)

        Badge(text = text.takeIf { it.isNotBlank() }, size = size)
    }
