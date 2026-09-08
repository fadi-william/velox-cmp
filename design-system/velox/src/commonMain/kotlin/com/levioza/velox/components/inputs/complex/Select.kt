@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.inputs.complex

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Describes an option displayed by selection components.
 *
 * @param value Current controlled value.
 * @param label Label text or label provider for the component.
 * @param enabled Whether user interaction is enabled.
 */
data class SelectOption<T>(
    val value: T,
    val label: String,
    val enabled: Boolean = true,
)

/**
 * Renders the Velox select component.
 *
 * The menu always matches the field width, and the field carries the same outline as the other
 * Velox text inputs. Choosing the option that is already selected clears the selection.
 *
 * @param options Options available for selection.
 * @param selected The currently selected value.
 * @param onSelected Callback invoked with the new selection, or null when the selected option
 *   is chosen again and the selection is cleared.
 * @param expanded Whether the component is currently expanded.
 * @param onExpandedChange Callback invoked when the expanded state should change.
 * @param modifier Modifier applied to the component.
 * @param label Label text or label provider for the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param enabled Whether user interaction is enabled.
 * @param isError Whether the component is displaying an error state.
 * @param supportingText Optional supporting text.
 */
@Composable
fun <T> Select(
    options: List<SelectOption<T>>,
    selected: T?,
    onSelected: (T?) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "Select an option",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
) {
    val selectedLabel = options.firstOrNull { it.value == selected }?.label.orEmpty()
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) onExpandedChange(it) },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selectedLabel,
            onValueChange = {},
            modifier =
                Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled)
                    .fillMaxWidth(),
            enabled = enabled,
            readOnly = true,
            label = label?.let { { Text(it) } },
            placeholder = { Text(placeholder) },
            supportingText = supportingText?.let { { Text(it) } },
            isError = isError,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            singleLine = true,
            textStyle = veloxTextFieldTextStyle(),
            shape = veloxTextFieldShape,
            colors = veloxTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            matchAnchorWidth = true,
        ) {
            options.forEach { option ->
                SelectMenuItem(
                    option = option,
                    selected = option.value == selected,
                    onClick = {
                        // Choosing the selected option again clears the selection.
                        onSelected(option.value.takeIf { it != selected })
                        onExpandedChange(false)
                    },
                )
            }
        }
    }
}
