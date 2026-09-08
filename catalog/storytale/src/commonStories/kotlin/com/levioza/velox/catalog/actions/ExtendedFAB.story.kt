@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.actions

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.actions.ExtendedFAB
import com.levioza.velox.components.actions.FABVariant
import org.jetbrains.compose.storytale.story

val `ExtendedFAB` by
    story(group = "Actions") {
        val text by parameter("Create")
        val variant by parameter(FABVariant.Primary)
        val showIcon by parameter(true)

        ExtendedFAB(
            text = text,
            onClick = {},
            variant = variant,
            icon =
                if (showIcon) {
                    {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            Modifier.size(24.dp),
                        )
                    }
                } else {
                    null
                },
        )
    }
