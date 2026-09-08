package com.levioza.velox.forms.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import com.levioza.velox.forms.FieldState

// Reports lost focus once the control has actually held it, so a field is not marked blurred by
// the focus simply passing by.
internal fun Modifier.onFocusLost(field: FieldState<*>): Modifier = composed {
    var hadFocus by remember { mutableStateOf(false) }
    onFocusChanged { focusState ->
        if (focusState.isFocused) {
            hadFocus = true
        } else if (hadFocus) {
            hadFocus = false
            field.onFocusLost()
        }
    }
}
