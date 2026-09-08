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
 * Represents a selected date range.
 *
 * @param start Start date in ISO YYYY-MM-DD format.
 * @param end End date in ISO YYYY-MM-DD format.
 */
data class DateRangeValue(
    val start: String,
    val end: String,
)

/**
 * Renders one read-only field that opens the Velox range calendar in a Material date-picker dialog.
 *
 * @param value Current controlled date range.
 * @param onValueChange Callback invoked with the confirmed date range.
 * @param modifier Modifier applied to the component.
 * @param label Label displayed by the component.
 * @param placeholder Placeholder shown when no range is present.
 * @param enabled Whether user interaction is enabled.
 * @param isError Whether the component is displaying an error state.
 * @param supportingText Optional supporting text.
 */
@Composable
fun DateRangeField(
    value: DateRangeValue,
    onValueChange: (DateRangeValue) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Date range",
    placeholder: String = "YYYY-MM-DD – YYYY-MM-DD",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
) {
    var showPicker by remember { mutableStateOf(false) }
    val displayValue =
        when {
            value.start.isBlank() -> ""
            value.end.isBlank() -> value.start
            else -> "${value.start} – ${value.end}"
        }

    Box(modifier = modifier) {
        TextField(
            value = displayValue,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = label,
            placeholder = placeholder,
            supportingText = supportingText,
            enabled = enabled,
            readOnly = true,
            isError = isError,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Choose date range",
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
            rememberDateRangeCalendarState(
                start = value.start.takeIf { it.isNotBlank() },
                end = value.end.takeIf { it.isNotBlank() },
                todayEpochDay = rememberTodayEpochDay(),
            )

        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    enabled = calendarState.hasCompleteRange,
                    onClick = {
                        val start = calendarState.startIsoDate
                        val end = calendarState.endIsoDate
                        if (start != null && end != null) {
                            onValueChange(DateRangeValue(start, end))
                        }
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
            DateRangeCalendar(state = calendarState)
        }
    }
}
