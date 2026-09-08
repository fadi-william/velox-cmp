package com.levioza.velox.forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.inputs.complex.Slider
import com.levioza.velox.forms.FieldState

/**
 * Renders a slider bound to [field].
 *
 * @param field Field to bind, from `form[Schema.someField]`.
 * @param label Label displayed by the component.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param valueRange Range the slider covers.
 * @param steps Number of discrete steps between the range's ends.
 * @param helperText Guidance shown while the field has no error.
 */
@Composable
fun FormSlider(
    field: FieldState<Float>,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    helperText: String? = null,
) {
    FormField(
        label = label,
        modifier = modifier,
        required = field.isRequired,
        helperText = helperText,
        errorMessage = field.error,
    ) {
        Slider(
            field.value,
            field.onValueChange,
            Modifier.fillMaxWidth(),
            enabled,
            valueRange,
            steps,
        )
    }
}
