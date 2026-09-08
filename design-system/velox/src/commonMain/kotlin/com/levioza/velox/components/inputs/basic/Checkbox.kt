package com.levioza.velox.components.inputs.basic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp

/**
 * Defines the supported checkbox state values.
 */
enum class CheckboxState { Checked, Unchecked, Indeterminate }

/**
 * Renders the Velox checkbox component.
 *
 * @param state Current state of the control.
 * @param onStateChange Callback invoked with a requested state change.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param label Label text or label provider for the component.
 */
@Composable
fun Checkbox(
    state: CheckboxState,
    onStateChange: (CheckboxState) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
) {
    val toggleState = when (state) {
        CheckboxState.Checked -> ToggleableState.On
        CheckboxState.Unchecked -> ToggleableState.Off
        CheckboxState.Indeterminate -> ToggleableState.Indeterminate
    }
    Row(
        modifier = modifier.clickable(enabled = enabled) {
            onStateChange(if (state == CheckboxState.Checked) CheckboxState.Unchecked else CheckboxState.Checked)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TriStateCheckbox(
            state = toggleState,
            onClick = null,
            enabled = enabled,
        )
        if (label != null) Text(label, modifier = Modifier.padding(start = 8.dp))
    }
}
