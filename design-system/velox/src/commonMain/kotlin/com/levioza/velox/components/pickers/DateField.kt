@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.pickers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

/**
 * Renders a read-only date field that opens the Velox calendar in a date-picker dialog.
 *
 * @param value Current date in ISO YYYY-MM-DD format.
 * @param onValueChange Callback invoked with the selected ISO date.
 * @param modifier Modifier applied to the component.
 * @param label Label displayed by the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param enabled Whether user interaction is enabled.
 * @param isError Whether the component is displaying an error state.
 * @param supportingText Optional supporting text.
 */
@Composable
fun DateField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Date",
    placeholder: String = "YYYY-MM-DD",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
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
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Choose date",
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
        val calendarState =
            rememberDateCalendarState(
                date = value.takeIf { it.isNotBlank() },
                todayEpochDay = rememberTodayEpochDay(),
            )

        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    enabled = calendarState.selectedIsoDate != null,
                    onClick = {
                        calendarState.selectedIsoDate?.let(onValueChange)
                        showPicker = false
                    },
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text("Cancel") }
            },
        ) {
            DateCalendar(state = calendarState)
        }
    }
}
