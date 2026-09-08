package com.levioza.velox.components.inputs.complex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Slider as MaterialSlider

/**
 * Renders the Velox slider component.
 *
 * @param value Current controlled value.
 * @param onValueChange Callback invoked with a requested value change.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether user interaction is enabled.
 * @param valueRange Range of values accepted by the slider.
 * @param steps Number of discrete intermediate slider steps.
 */
@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
) {
    MaterialSlider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
    )
}
