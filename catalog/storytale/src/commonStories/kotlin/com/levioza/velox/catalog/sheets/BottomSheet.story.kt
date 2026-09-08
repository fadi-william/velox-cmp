@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.sheets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.sheets.BottomSheet
import org.jetbrains.compose.storytale.story

val `BottomSheet` by
    story(group = "Sheets") {
        val content by parameter("Bottom sheet content")

        var visible by remember { mutableStateOf(false) }
        Button("Open bottom sheet", onClick = { visible = true })
        BottomSheet(visible, onDismissRequest = { visible = false }) {
            Text(content, Modifier.padding(24.dp))
        }
    }
