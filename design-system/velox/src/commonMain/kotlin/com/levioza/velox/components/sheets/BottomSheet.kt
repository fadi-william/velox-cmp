@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.sheets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Renders the Velox bottom sheet component.
 *
 * @param visible Whether the component is currently visible.
 * @param onDismissRequest Callback invoked when dismissal is requested.
 * @param modifier Modifier applied to the component.
 * @param content Content rendered inside the component.
 */
@Composable
fun BottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (visible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            content = content,
        )
    }
}
