package com.levioza.velox.components.inputs.complex

import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Defines the supported chip variant values.
 */
enum class ChipVariant { Assist, Filter, Input, Suggestion }

/**
 * Renders the Velox chip component.
 *
 * @param label Label text or label provider for the component.
 * @param onClick Callback invoked when the component is activated.
 * @param modifier Modifier applied to the component.
 * @param variant Visual variant of the component.
 * @param selected The currently selected value.
 * @param enabled Whether user interaction is enabled.
 * @param leadingIcon Optional icon displayed before the main content.
 * @param trailingIcon Optional icon displayed after the main content.
 */
@Composable
fun Chip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ChipVariant = ChipVariant.Assist,
    selected: Boolean = false,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    when (variant) {
        ChipVariant.Assist -> AssistChip(
            onClick = onClick,
            label = { Text(label) },
            modifier = modifier,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
        ChipVariant.Filter -> FilterChip(
            selected = selected,
            onClick = onClick,
            label = { Text(label) },
            modifier = modifier,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
        ChipVariant.Input -> InputChip(
            selected = selected,
            onClick = onClick,
            label = { Text(label) },
            modifier = modifier,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
        ChipVariant.Suggestion -> SuggestionChip(
            onClick = onClick,
            label = { Text(label) },
            modifier = modifier,
            enabled = enabled,
        )
    }
}
