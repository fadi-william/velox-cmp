@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.communication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.communication.Dialog
import com.levioza.velox.components.inputs.basic.Button
import org.jetbrains.compose.storytale.story

val `Dialog` by
    story(group = "Communication") {
        val title by parameter("Delete item?")
        val message by parameter("This action cannot be undone.")
        val confirmLabel by parameter("Delete")
        val dismissLabel by parameter("Cancel")

        var visible by remember { mutableStateOf(false) }
        Button("Open dialog", onClick = { visible = true })
        Dialog(
            visible = visible,
            onDismissRequest = { visible = false },
            title = title,
            confirmLabel = confirmLabel,
            onConfirm = { visible = false },
            message = message.takeIf { it.isNotBlank() },
            dismissLabel = dismissLabel.takeIf { it.isNotBlank() },
        )
    }
