package com.levioza.velox.forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.levioza.velox.components.inputs.complex.Select
import com.levioza.velox.components.inputs.complex.SelectOption
import com.levioza.velox.forms.FieldState

/**
 * Renders a select bound to [field], holding the menu's expanded state for the caller.
 *
 * @param field Field to bind, from `form[Schema.someField]`; hold it as a nullable type so the
 *   control can start with nothing selected.
 * @param options Options offered by the component.
 * @param label Label displayed by the component, marked when the field is required.
 * @param modifier Modifier applied to the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param enabled Whether user interaction is enabled.
 * @param helperText Guidance shown while the field has no error.
 */
@Composable
fun <T> FormSelect(
    field: FieldState<T?>,
    options: List<SelectOption<T>>,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "Select an option",
    enabled: Boolean = true,
    helperText: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    Select(
        options = options,
        selected = field.value,
        onSelected = { value ->
            field.onValueChange(value)
            field.onFocusLost()
        },
        expanded = expanded,
        onExpandedChange = { isExpanded ->
            expanded = isExpanded
            if (!isExpanded) field.onFocusLost()
        },
        modifier = modifier.fillMaxWidth(),
        label = field.labelWithRequiredMarker(label),
        placeholder = placeholder,
        enabled = enabled,
        isError = field.error != null,
        supportingText = field.supportingText(helperText),
    )
}
