@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.communication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Snackbar as MaterialSnackbar

/**
 * Renders the Velox snackbar component.
 *
 * @param message Message displayed to the user.
 * @param modifier Modifier applied to the component.
 * @param actionLabel Optional label for the snackbar action.
 * @param onAction Callback invoked when the snackbar action is selected.
 * @param dismissAction Optional composable used to dismiss the snackbar.
 */
@Composable
fun Snackbar(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    dismissAction: (@Composable () -> Unit)? = null,
) {
    MaterialSnackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = message,
                modifier = Modifier.weight(1f),
            )
            if (actionLabel != null && onAction != null) {
                TextButton(
                    onClick = onAction,
                    modifier = Modifier.defaultMinSize(minWidth = 1.dp),
                    colors =
                        ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                    contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
                ) {
                    Text(actionLabel)
                }
            }
            dismissAction?.invoke()
        }
    }
}
