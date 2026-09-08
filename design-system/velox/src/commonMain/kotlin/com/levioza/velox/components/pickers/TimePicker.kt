@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.pickers

import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.material3.TimePicker as MaterialTimePicker

/**
 * Represents an hour and minute selection.
 *
 * @param hour Hour component of the time value.
 * @param minute Minute component of the time value.
 */
data class TimeValue(
    val hour: Int,
    val minute: Int,
)

/**
 * Renders the Velox time picker component.
 *
 * @param value Current controlled value.
 * @param onValueChange Callback invoked with a requested value change.
 * @param modifier Modifier applied to the component.
 * @param is24Hour Whether time is displayed using a 24-hour clock.
 */
@Composable
fun TimePicker(
    value: TimeValue,
    onValueChange: (TimeValue) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false,
) {
    val state = rememberTimePickerState(
        initialHour = value.hour.coerceIn(0, 23),
        initialMinute = value.minute.coerceIn(0, 59),
        is24Hour = is24Hour,
    )
    LaunchedEffect(state.hour, state.minute) {
        val next = TimeValue(state.hour, state.minute)
        if (next != value) onValueChange(next)
    }
    MaterialTimePicker(state = state, modifier = modifier)
}
