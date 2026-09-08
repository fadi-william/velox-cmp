@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Describes an option displayed by a segmented button.
 *
 * @param value Current controlled value.
 * @param label Label text or label provider for the component.
 * @param enabled Whether user interaction is enabled.
 * @param icon Icon content displayed by the component.
 */
data class SegmentedButtonOption<T>(
    val value: T,
    val label: String,
    val enabled: Boolean = true,
    val icon: (@Composable () -> Unit)? = null,
)

/**
 * Renders the Velox segmented button component.
 *
 * @param options Options available for selection.
 * @param selected The currently selected value.
 * @param onSelected Callback invoked when the selection changes.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun <T> SegmentedButton(
    options: List<SegmentedButtonOption<T>>,
    selected: T,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        options.forEachIndexed { index, option ->
            val isSelected = option.value == selected
            Surface(
                modifier = Modifier.weight(1f),
                selected = isSelected,
                enabled = option.enabled,
                onClick = { onSelected(option.value) },
                color = if (isSelected) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                },
                contentColor = if (isSelected) {
                    MaterialTheme.colorScheme.onSecondaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                shape = RoundedCornerShape(
                    topStart = if (index == 0) 24.dp else 0.dp,
                    bottomStart = if (index == 0) 24.dp else 0.dp,
                    topEnd = if (index == options.lastIndex) 24.dp else 0.dp,
                    bottomEnd = if (index == options.lastIndex) 24.dp else 0.dp,
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                ),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    option.icon?.invoke()
                    Text(option.label)
                }
            }
        }
    }
}
