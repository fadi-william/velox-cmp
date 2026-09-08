@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.inputs.complex

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Renders the Velox autocomplete component.
 *
 * Matching is case-insensitive and limited to the first 50 results to keep the menu responsive.
 * Choosing the option that is already selected clears the selection and the text.
 * The editable menu preserves input focus while typing and always matches the field width.
 *
 * @param value Current controlled value.
 * @param onValueChange Callback invoked with a requested value change.
 * @param options Options available for selection.
 * @param onSelected Callback invoked with the chosen option, or null when the selected option is
 *   chosen again and both the selection and the text are cleared.
 * @param modifier Modifier applied to the component.
 * @param label Label text or label provider for the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param enabled Whether user interaction is enabled.
 * @param isError Whether the component is displaying an error state.
 * @param supportingText Optional supporting text.
 */
@Composable
fun <T> Autocomplete(
    value: String,
    onValueChange: (String) -> Unit,
    options: List<SelectOption<T>>,
    onSelected: (T?) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "Search",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    // The field only holds the typed text, so the chosen option is remembered separately: while
    // the query is being edited the text no longer equals the option's label.
    var selectedLabel by remember { mutableStateOf<String?>(null) }
    val matches = remember(value, options) {
        options.asSequence()
            .filter { it.label.contains(value, ignoreCase = true) }
            .take(50)
            .toList()
    }
    val showMenu = enabled && menuExpanded && value.isNotBlank() && matches.isNotEmpty()

    ExposedDropdownMenuBox(
        expanded = showMenu,
        onExpandedChange = {
            if (enabled && value.isNotBlank() && matches.isNotEmpty()) {
                menuExpanded = it
            }
        },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
                menuExpanded = newValue.isNotBlank()
            },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, enabled)
                .fillMaxWidth(),
            enabled = enabled,
            label = label?.let { { Text(it) } },
            placeholder = { Text(placeholder) },
            supportingText = supportingText?.let { { Text(it) } },
            isError = isError,
            singleLine = true,
            textStyle = veloxTextFieldTextStyle(),
            shape = veloxTextFieldShape,
            colors = veloxTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = showMenu,
            onDismissRequest = { menuExpanded = false },
            matchAnchorWidth = true,
        ) {
            matches.forEach { option ->
                SelectMenuItem(
                    option = option,
                    selected = option.label == selectedLabel,
                    onClick = {
                        menuExpanded = false
                        // Choosing the selected option again clears both the selection and the
                        // text it had filled in.
                        val isSelected = option.label == selectedLabel
                        selectedLabel = if (isSelected) null else option.label
                        onValueChange(if (isSelected) "" else option.label)
                        onSelected(if (isSelected) null else option.value)
                    },
                )
            }
        }
    }
}
