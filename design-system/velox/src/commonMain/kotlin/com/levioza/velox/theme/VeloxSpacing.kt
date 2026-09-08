package com.levioza.velox.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** A small semantic spacing scale shared by Velox components. */
@Immutable
object VeloxSpacing {
    /** Extra-small spacing for tightly grouped content. */
    val extraSmall: Dp = 4.dp
    /** Small spacing between closely related elements. */
    val small: Dp = 8.dp
    /** Default spacing between component elements. */
    val medium: Dp = 16.dp
    /** Large spacing between content sections. */
    val large: Dp = 24.dp
    /** Extra-large spacing for prominent section separation. */
    val extraLarge: Dp = 32.dp
}
