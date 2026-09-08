package com.levioza.velox.forms.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.inputs.basic.RadioGroup
import com.levioza.velox.forms.FieldState

/**
 * Renders a radio group bound to [field].
 *
 * @param field Field to bind, from `form[Schema.someField]`; hold it as a nullable type so the
 *   control can start with nothing selected.
 * @param options Options offered by the component.
 * @param optionLabel Label shown for an option.
 * @param label Label displayed by the component.
 * @param modifier Modifier applied to the component.
 * @param helperText Guidance shown while the field has no error.
 */
@Composable
fun <T> FormRadioGroup(
    field: FieldState<T?>,
    options: List<T>,
    optionLabel: (T) -> String,
    label: String,
    modifier: Modifier = Modifier,
    helperText: String? = null,
) {
    FormField(
        label = label,
        modifier = modifier,
        required = field.isRequired,
        helperText = helperText,
        errorMessage = field.error,
    ) {
        RadioGroup(
            options = options,
            selected = field.value,
            onSelected = { value ->
                field.onValueChange(value)
                field.onFocusLost()
            },
            label = optionLabel,
        )
    }
}
