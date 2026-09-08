package com.levioza.velox.components.inputs.basic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.material3.RadioButton as MaterialRadioButton

/**
 * Renders the Velox radio button component.
 *
 * @param selected The currently selected value.
 * @param onClick Callback invoked when the component is activated.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param label Label text or label provider for the component.
 */
@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
) {
    Row(
        modifier = modifier.selectable(
            selected = selected,
            enabled = enabled,
            role = Role.RadioButton,
            onClick = onClick,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MaterialRadioButton(selected = selected, onClick = null, enabled = enabled)
        if (label != null) Text(label, modifier = Modifier.padding(start = 8.dp))
    }
}

/**
 * Renders the Velox radio group component.
 *
 * @param options Options available for selection.
 * @param selected The currently selected value.
 * @param onSelected Callback invoked when the selection changes.
 * @param label Label text or label provider for the component.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun <T> RadioGroup(
    options: List<T>,
    selected: T?,
    onSelected: (T) -> Unit,
    label: (T) -> String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.selectableGroup()) {
        options.forEach { option ->
            RadioButton(
                selected = option == selected,
                onClick = { onSelected(option) },
                label = label(option),
            )
        }
    }
}
