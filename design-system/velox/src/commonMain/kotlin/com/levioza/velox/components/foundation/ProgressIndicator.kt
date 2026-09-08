package com.levioza.velox.components.foundation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Defines the supported progress indicator type values.
 */
enum class ProgressIndicatorType { Linear, Circular }
/**
 * Defines the supported progress indicator variant values.
 */
enum class ProgressIndicatorVariant { Determinate, Indeterminate }
/**
 * Defines the supported progress indicator size values.
 */
enum class ProgressIndicatorSize { ExtraSmall, Small, Medium, Large }

/**
 * Renders the Velox progress indicator component.
 *
 * @param modifier Modifier applied to the component.
 * @param type Indicator type to render.
 * @param variant Visual variant of the component.
 * @param progress Current determinate progress value.
 * @param size Visual size of the component.
 */
@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    type: ProgressIndicatorType = ProgressIndicatorType.Circular,
    variant: ProgressIndicatorVariant = ProgressIndicatorVariant.Indeterminate,
    progress: Float = 0f,
    size: ProgressIndicatorSize = ProgressIndicatorSize.Medium,
) {
    val indicatorSize = when (size) {
        ProgressIndicatorSize.ExtraSmall -> 16.dp
        ProgressIndicatorSize.Small -> 24.dp
        ProgressIndicatorSize.Medium -> 40.dp
        ProgressIndicatorSize.Large -> 56.dp
    }
    val strokeWidth = when (size) {
        ProgressIndicatorSize.ExtraSmall -> 2.dp
        ProgressIndicatorSize.Small -> 3.dp
        ProgressIndicatorSize.Medium -> 4.dp
        ProgressIndicatorSize.Large -> 5.dp
    }
    when (type) {
        ProgressIndicatorType.Linear -> {
            if (variant == ProgressIndicatorVariant.Determinate) {
                LinearProgressIndicator(
                    progress = { progress.coerceIn(0f, 1f) },
                    modifier = modifier.fillMaxWidth(),
                )
            } else {
                LinearProgressIndicator(modifier = modifier.fillMaxWidth())
            }
        }
        ProgressIndicatorType.Circular -> {
            // requiredSize rather than size: a circular indicator has to stay square, and an
            // incoming width or height constraint would otherwise stretch it into an ellipse that
            // spins around an off-centre anchor.
            if (variant == ProgressIndicatorVariant.Determinate) {
                CircularProgressIndicator(
                    progress = { progress.coerceIn(0f, 1f) },
                    modifier = modifier.requiredSize(indicatorSize),
                    strokeWidth = strokeWidth,
                )
            } else {
                CircularProgressIndicator(
                    modifier = modifier.requiredSize(indicatorSize),
                    strokeWidth = strokeWidth,
                )
            }
        }
    }
}
