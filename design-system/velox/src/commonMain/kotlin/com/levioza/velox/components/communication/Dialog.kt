@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.communication

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Renders the Velox dialog component.
 *
 * @param visible Whether the component is currently visible.
 * @param onDismissRequest Callback invoked when dismissal is requested.
 * @param title Primary title displayed by the component.
 * @param confirmLabel Label for the confirmation action.
 * @param onConfirm Callback invoked when the confirmation action is selected.
 * @param modifier Modifier applied to the component.
 * @param message Message displayed to the user.
 * @param dismissLabel Optional label for the dismiss action.
 */
@Composable
fun Dialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null,
    dismissLabel: String? = null,
) {
    if (!visible) return
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = { Text(title) },
        text = message?.let { value -> { Text(value) } },
        confirmButton = { TextButton(onClick = onConfirm) { Text(confirmLabel) } },
        dismissButton = dismissLabel?.let { label ->
            { TextButton(onClick = onDismissRequest) { Text(label) } }
        },
    )
}
