@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.foundation

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.foundation.ProgressIndicator
import com.levioza.velox.components.foundation.ProgressIndicatorSize
import com.levioza.velox.components.foundation.ProgressIndicatorType
import com.levioza.velox.components.foundation.ProgressIndicatorVariant
import org.jetbrains.compose.storytale.story

val `ProgressIndicator` by
    story(group = "Foundation") {
        val type by parameter(ProgressIndicatorType.Circular)
        val variant by parameter(ProgressIndicatorVariant.Indeterminate)
        val size by parameter(ProgressIndicatorSize.Medium)
        val progress by parameter(0.64f)

        ProgressIndicator(
            // A width belongs to the linear track only; the circular indicator sizes itself.
            modifier =
                if (type == ProgressIndicatorType.Linear) Modifier.width(180.dp) else Modifier,
            type = type,
            variant = variant,
            size = size,
            progress = progress,
        )
    }
