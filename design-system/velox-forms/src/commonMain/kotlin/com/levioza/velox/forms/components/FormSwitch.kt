package com.levioza.velox.forms.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.inputs.basic.Switch
import com.levioza.velox.forms.FieldState

/**
 * Renders a switch bound to [field].
 *
 * @param field Field to bind, from `form[Schema.someField]`.
 * @param label Label displayed by the component.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param helperText Guidance shown while the field has no error.
 */
@Composable
fun FormSwitch(
    field: FieldState<Boolean>,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    helperText: String? = null,
) {
    FormField(
        label = label,
        modifier = modifier,
        required = field.isRequired,
        helperText = helperText,
        errorMessage = field.error,
        labelPlacement = FormFieldLabelPlacement.Beside,
    ) {
        Switch(checked = field.value, onCheckedChange = field.onValueChange, enabled = enabled)
    }
}
