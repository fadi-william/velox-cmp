@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.communication

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import kotlin.math.hypot

/**
 * Renders an animated loading placeholder.
 *
 * @param modifier Modifier applied to the component.
 * @param shape Shape used to clip the component.
 * @param color Base color used to draw the component.
 * @param shimmerColor Highlight color swept continuously across the component.
 * @param shimmer Whether the shimmer animation is enabled.
 */
@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    color: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    shimmerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    shimmer: Boolean = true,
) {
    val transition = rememberInfiniteTransition(label = "Skeleton shimmer")
    val progress by
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 1_200, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart,
                ),
            label = "Skeleton shimmer progress",
        )

    Box(
        modifier =
            modifier
                .height(20.dp)
                .clip(shape)
                .background(color)
                .drawWithContent {
                    drawContent()
                    if (shimmer) {
                        // The sweep runs along the shape's diagonal, so one tile is that
                        // diagonal's length. Travelling exactly one tile along that same
                        // direction is what makes the repeat seamless: shifting horizontally
                        // instead lands mid-pattern on anything that is not a thin line.
                        val period = hypot(size.width, size.height).coerceAtLeast(1f)
                        val directionX = size.width / period
                        val directionY = size.height / period
                        val travelled = progress * period
                        drawRect(
                            brush =
                                Brush.linearGradient(
                                    colorStops =
                                        arrayOf(
                                            0f to Color.Transparent,
                                            0.35f to Color.Transparent,
                                            0.5f to shimmerColor,
                                            0.65f to Color.Transparent,
                                            1f to Color.Transparent,
                                        ),
                                    start =
                                        Offset(
                                            (travelled - period) * directionX,
                                            (travelled - period) * directionY,
                                        ),
                                    end = Offset(travelled * directionX, travelled * directionY),
                                    tileMode = TileMode.Repeated,
                                ),
                        )
                    }
                },
    )
}
