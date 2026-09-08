@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.foundation

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.foundation.Divider
import com.levioza.velox.components.foundation.DividerOrientation
import com.levioza.velox.components.foundation.DividerVariant
import org.jetbrains.compose.storytale.story

val `Divider` by
    story(group = "Foundation") {
        val variant by parameter(DividerVariant.Full)
        val orientation by parameter(DividerOrientation.Horizontal)

        Divider(
            modifier = Modifier.size(320.dp, 120.dp),
            variant = variant,
            orientation = orientation,
        )
    }
