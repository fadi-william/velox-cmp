@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.communication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.levioza.velox.components.communication.Snackbar
import com.levioza.velox.components.inputs.basic.Button
import kotlinx.coroutines.delay
import org.jetbrains.compose.storytale.story

val `Snackbar` by
    story(group = "Communication") {
        val message by parameter("Changes saved")
        val actionLabel by parameter("Undo")
        val dismissAfterSeconds by parameter(4)

        var visible by remember { mutableStateOf(false) }
        if (visible && dismissAfterSeconds > 0) {
            LaunchedEffect(Unit) {
                delay(dismissAfterSeconds * 1_000L)
                visible = false
            }
        }

        Box(Modifier.fillMaxSize()) {
            Button(
                text = "Show snackbar",
                onClick = { visible = true },
                modifier = Modifier.align(Alignment.Center),
            )
            if (visible) {
                Snackbar(
                    message = message,
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                    actionLabel = actionLabel.takeIf { it.isNotBlank() },
                    onAction = { visible = false },
                )
            }
        }
    }
