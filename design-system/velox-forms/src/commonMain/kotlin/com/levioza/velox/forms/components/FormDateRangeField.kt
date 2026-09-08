package com.levioza.velox.forms.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.pickers.DateRangeField
import com.levioza.velox.components.pickers.DateRangeValue
import com.levioza.velox.forms.FieldState

/**
 * Renders a date range field bound to [field].
 *
 * @param field Field to bind, from `form[Schema.someField]`.
 * @param label Label displayed by the component, marked when the field is required.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param helperText Guidance shown while the field has no error.
 */
@Composable
fun FormDateRangeField(
    field: FieldState<DateRangeValue>,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    helperText: String? = null,
) {
    DateRangeField(
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
