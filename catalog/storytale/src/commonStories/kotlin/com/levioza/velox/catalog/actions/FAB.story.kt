@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.actions

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.actions.FAB
import com.levioza.velox.components.actions.FABSize
import com.levioza.velox.components.actions.FABVariant
import org.jetbrains.compose.storytale.story

val `FAB` by
    story(group = "Actions") {
        val size by parameter(FABSize.Medium)
        val variant by parameter(FABVariant.Primary)

        FAB(
            onClick = {},
            icon = { Icon(Icons.Default.Add, contentDescription = "Add", Modifier.size(24.dp)) },
            variant = variant,
            size = size,
        )
    }
