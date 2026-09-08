package com.levioza.velox.components.surfaces

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Renders the Velox collapsible component.
 *
 * @param expanded Whether the component is currently expanded.
 * @param modifier Modifier applied to the component.
 * @param header Header content displayed by the component.
 * @param content Content rendered inside the component.
 */
@Composable
fun Collapsible(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier) {
        header()
        AnimatedVisibility(visible = expanded) {
            Column(content = content)
        }
    }
}
