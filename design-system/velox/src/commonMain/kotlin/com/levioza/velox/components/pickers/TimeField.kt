@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.pickers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.TextField
import androidx.compose.material3.TimePicker as MaterialTimePicker

/**
 * Renders a read-only time field that opens a Material time-picker dialog.
 *
 * @param value Current time in HH:MM format.
 * @param onValueChange Callback invoked with the selected time.
 * @param modifier Modifier applied to the component.
 * @param label Label displayed by the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param enabled Whether user interaction is enabled.
 * @param isError Whether the component is displaying an error state.
 * @param supportingText Optional supporting text.
 * @param is24Hour Whether the picker uses 24-hour time instead of AM/PM.
 */
@Composable
fun TimeField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Time",
    placeholder: String = "HH:MM",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    is24Hour: Boolean = false,
) {
    var showPicker by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = label,
            placeholder = placeholder,
            enabled = enabled,
            readOnly = true,
            isError = isError,
            supportingText = supportingText,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Choose time",
                    modifier = Modifier.size(24.dp),
                )
            },
        )
        // The overlay only forwards taps to the dialog, so it draws no indication of its own:
        // a ripple here would wash over the whole field, label included.
        Box(
            modifier =
                Modifier.matchParentSize()
                    .pointerHoverIcon(if (enabled) PointerIcon.Hand else PointerIcon.Default)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = enabled,
                    ) {
                        showPicker = true
                    },
        )
    }

    if (showPicker) {
        val initialTime = value.toTimeValue() ?: TimeValue(12, 0)
        val pickerState =
            rememberTimePickerState(
                initialHour = initialTime.hour,
                initialMinute = initialTime.minute,
                is24Hour = is24Hour,
            )
        AlertDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onValueChange(
                            pickerState.hour.toString().padStart(2, '0') +
                                ":" +
                                pickerState.minute.toString().padStart(2, '0'),
                        )
                        showPicker = false
                    },
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text("Cancel") }
            },
            text = { MaterialTimePicker(state = pickerState) },
        )
    }
}

private fun String.toTimeValue(): TimeValue? {
    val parts = split(':')
    if (parts.size != 2) return null
    val hour = parts[0].toIntOrNull() ?: return null
    val minute = parts[1].toIntOrNull() ?: return null
    return TimeValue(hour, minute).takeIf { hour in 0..23 && minute in 0..59 }
}
