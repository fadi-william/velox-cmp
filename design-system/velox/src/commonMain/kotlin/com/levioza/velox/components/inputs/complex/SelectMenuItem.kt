package com.levioza.velox.components.inputs.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Renders one option of a select or autocomplete menu, marking the selected one with a tinted row
 * and a trailing check.
 *
 * @param option Option rendered by the item.
 * @param selected Whether the option is the current selection.
 * @param onClick Callback invoked when the option is chosen.
 * @param modifier Modifier applied to the component.
 */
@Composable
internal fun <T> SelectMenuItem(
    option: SelectOption<T>,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        text = { Text(option.label) },
        onClick = onClick,
        modifier =
            if (selected) {
                modifier.background(MaterialTheme.colorScheme.secondaryContainer)
            } else {
                modifier
            },
        enabled = option.enabled,
        trailingIcon =
            if (selected) {
                {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SelectCheckSize),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            } else {
                null
            },
        colors =
            if (selected) {
                MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    trailingIconColor = MaterialTheme.colorScheme.primary,
                )
            } else {
                MenuDefaults.itemColors()
            },
    )
}

private val SelectCheckSize = 24.dp
