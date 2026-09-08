@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.pickers

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.pickers.DateRangeField
import com.levioza.velox.components.pickers.DateRangeValue
import org.jetbrains.compose.storytale.story

val `DateRangeField` by
    story(group = "Pickers") {
        val label by parameter("Date range")
        val supportingText by parameter("")
        val initialStart by parameter("2026-09-08")
        val initialEnd by parameter("2026-09-15")
        val enabled by parameter(true)
        val isError by parameter(false)

        var value by
            remember(initialStart, initialEnd) {
                mutableStateOf(DateRangeValue(initialStart, initialEnd))
            }
        DateRangeField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(360.dp),
            label = label,
            enabled = enabled,
            isError = isError,
            supportingText = supportingText.takeIf { it.isNotBlank() },
        )
    }
