package com.levioza.velox.components.inputs.basic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Switch as MaterialSwitch

/**
 * Renders the Velox switch component.
 *
 * @param checked Whether the control is checked.
 * @param onCheckedChange Callback invoked with a requested checked-state change.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param label Label text or label provider for the component.
 */
@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
) {
    Row(
        modifier = modifier.clickable(enabled = enabled) { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        MaterialSwitch(checked = checked, onCheckedChange = null, enabled = enabled)
        if (label != null) Text(label)
    }
}
