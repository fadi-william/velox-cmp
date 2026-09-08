package com.levioza.velox.forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.inputs.complex.Autocomplete
import com.levioza.velox.components.inputs.complex.SelectOption
import com.levioza.velox.forms.FieldState

/**
 * Renders an autocomplete bound to [field], whose value is the text typed in the control.
 *
 * @param field Field to bind, from `form[Schema.someField]`.
 * @param options Options offered by the component.
 * @param label Label displayed by the component, marked when the field is required.
 * @param modifier Modifier applied to the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param enabled Whether user interaction is enabled.
 * @param helperText Guidance shown while the field has no error.
 * @param onSelected Callback invoked with the value behind the chosen option, or null when the
 *   selected option is chosen again and the field is cleared.
 */
@Composable
fun <T> FormAutocomplete(
    field: FieldState<String>,
    options: List<SelectOption<T>>,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    enabled: Boolean = true,
    helperText: String? = null,
    onSelected: (T?) -> Unit = {},
) {
    Autocomplete(
        value = field.value,
        onValueChange = field.onValueChange,
        options = options,
        onSelected = { value ->
            onSelected(value)
            field.onFocusLost()
        },
        modifier = modifier.fillMaxWidth().onFocusLost(field),
        label = field.labelWithRequiredMarker(label),
        placeholder = placeholder,
        enabled = enabled,
        isError = field.error != null,
        supportingText = field.supportingText(helperText),
    )
}
