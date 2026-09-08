@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.pickers

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.pickers.TimePicker
import com.levioza.velox.components.pickers.TimeValue
import org.jetbrains.compose.storytale.story

val `TimePicker` by
    story(group = "Pickers") {
        val openLabel by parameter("Open time picker")
        val initialHour by parameter(10)
        val initialMinute by parameter(30)
        val is24Hour by parameter(false)

        var time by
            remember(initialHour, initialMinute) {
                mutableStateOf(TimeValue(initialHour, initialMinute))
            }
        var visible by remember { mutableStateOf(false) }
        Button(openLabel, onClick = { visible = true })
        if (visible) {
            var draftTime by remember { mutableStateOf(time) }
            AlertDialog(
                onDismissRequest = { visible = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            time = draftTime
                            visible = false
                        },
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { visible = false }) { Text("Cancel") }
                },
                text = {
                    TimePicker(
                        draftTime,
                        onValueChange = { draftTime = it },
                        is24Hour = is24Hour,
                    )
                },
            )
        }
    }
