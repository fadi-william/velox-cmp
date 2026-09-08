package com.levioza.velox.forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.inputs.complex.TextField
import com.levioza.velox.forms.FieldState

/**
 * Renders a text field bound to [field], reporting edits and lost focus back to the form so the
 * form's validation mode decides when the error appears.
 *
 * The label is the control's floating label and the error replaces the helper text below it, so a
 * bound field looks like any other Velox text field.
 *
 * @param field Field to bind, from `form[Schema.someField]`.
 * @param label Label displayed by the component, marked when the field is required.
 * @param modifier Modifier applied to the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param enabled Whether user interaction is enabled.
 * @param helperText Guidance shown while the field has no error.
 * @param singleLine Whether the field is limited to one line.
 */
@Composable
fun FormTextField(
    field: FieldState<String>,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    helperText: String? = null,
    singleLine: Boolean = true,
) {
    TextField(
        value = field.value,
        onValueChange = field.onValueChange,
        modifier = modifier.fillMaxWidth().onFocusLost(field),
        label = field.labelWithRequiredMarker(label),
        placeholder = placeholder,
        supportingText = field.supportingText(helperText),
        enabled = enabled,
        isError = field.error != null,
        singleLine = singleLine,
    )
}
