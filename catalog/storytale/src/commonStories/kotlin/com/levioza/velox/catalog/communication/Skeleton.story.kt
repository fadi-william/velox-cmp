@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.communication

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.communication.Skeleton
import org.jetbrains.compose.storytale.story

val `Skeleton` by
    story(group = "Communication") {
        val width by parameter(240)
        val height by parameter(64)
        val cornerRadius by parameter(12)
        val shimmer by parameter(true)

        Skeleton(
            modifier = Modifier.size(width.dp, height.dp),
            shape = RoundedCornerShape(cornerRadius.dp),
            shimmer = shimmer,
        )
    }
