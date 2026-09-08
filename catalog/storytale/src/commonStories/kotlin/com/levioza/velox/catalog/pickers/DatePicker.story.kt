@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.pickers

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.pickers.DatePicker
import org.jetbrains.compose.storytale.story

val `DatePicker` by
    story(group = "Pickers") {
        val openLabel by parameter("Open date picker")
        val confirmLabel by parameter("OK")
        val dismissLabel by parameter("Cancel")

        var date by remember { mutableStateOf<Long?>(null) }
        var visible by remember { mutableStateOf(false) }
        Button(openLabel, onClick = { visible = true })
        if (visible) {
            var draftDate by remember { mutableStateOf(date) }
            DatePickerDialog(
                onDismissRequest = { visible = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            date = draftDate
                            visible = false
                        },
                    ) {
                        Text(confirmLabel)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { visible = false }) { Text(dismissLabel) }
                },
            ) {
                DatePicker(draftDate, onDateSelected = { draftDate = it })
            }
        }
    }
