@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.sheets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.sheets.SideSheet
import com.levioza.velox.components.sheets.SideSheetAnchor
import org.jetbrains.compose.storytale.story

val `SideSheet` by
    story(group = "Sheets") {
        val content by parameter("Side sheet content")
        val anchor by parameter(SideSheetAnchor.End)

        var visible by remember { mutableStateOf(false) }
        Box(Modifier.fillMaxSize()) {
            Button("Open side sheet", { visible = true })
            SideSheet(visible, { visible = false }, anchor = anchor) {
                Text(content, Modifier.padding(24.dp))
            }
        }
    }
