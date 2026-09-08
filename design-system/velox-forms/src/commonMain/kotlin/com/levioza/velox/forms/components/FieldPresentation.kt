package com.levioza.velox.forms.components

import com.levioza.velox.forms.FieldState

// Controls that carry their own floating label render the required marker in that label, so it
// reads the same way as the external label a FormField draws.
internal fun FieldState<*>.labelWithRequiredMarker(label: String): String =
    if (isRequired) "$label *" else label

// A field shows its error in place of its guidance, so the control's supporting slot carries one
// or the other rather than stacking both. The slot is never left empty: a bound field can gain an
// error on any keystroke, and dropping the line would resize the field as the user types.
internal fun FieldState<*>.supportingText(helperText: String?): String =
    error ?: helperText.orEmpty()
