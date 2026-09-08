package com.levioza.velox.forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.pickers.DateField
import com.levioza.velox.forms.FieldState

/**
 * Renders a date field bound to [field], whose value is an ISO YYYY-MM-DD date.
 *
 * @param field Field to bind, from `form[Schema.someField]`.
 * @param label Label displayed by the component, marked when the field is required.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param helperText Guidance shown while the field has no error.
 */
@Composable
fun FormDateField(
    field: FieldState<String>,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    helperText: String? = null,
) {
    DateField(
        value = field.value,
        onValueChange = { value ->
            field.onValueChange(value)
            field.onFocusLost()
        },
        modifier = modifier.fillMaxWidth(),
        label = field.labelWithRequiredMarker(label),
        supportingText = field.supportingText(helperText),
        enabled = enabled,
        isError = field.error != null,
    )
}
