@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.sheets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp

/** Defines the supported side sheet anchor values. */
enum class SideSheetAnchor {
    /** Anchors the sheet to the logical start edge. */
    Start,

    /** Anchors the sheet to the logical end edge. */
    End,
}

/**
 * Displays modal content that slides over the page from a side edge.
 *
 * @param visible Whether the component is currently visible.
 * @param onDismissRequest Callback invoked when the background scrim is selected.
 * @param modifier Modifier applied to the full-screen sheet layer.
 * @param anchor Screen edge from which the sheet enters.
 * @param content Content rendered inside the sheet.
 */
@Composable
fun SideSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    anchor: SideSheetAnchor = SideSheetAnchor.End,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrimInteractionSource = remember { MutableInteractionSource() }
    val sheetInteractionSource = remember { MutableInteractionSource() }
    val alignment =
        if (anchor == SideSheetAnchor.End) Alignment.CenterEnd else Alignment.CenterStart

    // The panel starts outside the sheet layer and slides in, so the layer clips: without this
    // the moving rectangle paints over whatever sits beside the sheet's own area.
    Box(modifier = modifier.fillMaxSize().clipToBounds()) {
        AnimatedVisibility(
            visible = visible,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier =
                    Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f))
                        .clickable(
                            interactionSource = scrimInteractionSource,
                            indication = null,
                            onClick = onDismissRequest,
                        ),
            )
        }

        AnimatedVisibility(
            visible = visible,
            modifier = Modifier.fillMaxHeight().align(alignment),
            enter =
                slideInHorizontally { width ->
                    if (anchor == SideSheetAnchor.End) width else -width
                },
            exit =
                slideOutHorizontally { width ->
                    if (anchor == SideSheetAnchor.End) width else -width
                },
        ) {
            Surface(
                modifier =
                    Modifier.fillMaxHeight()
                        .widthIn(max = 400.dp)
                        .fillMaxWidth(0.88f)
                        .clickable(
                            interactionSource = sheetInteractionSource,
                            indication = null,
                            onClick = {},
                        ),
                shape =
                    if (anchor == SideSheetAnchor.End) {
                        RoundedCornerShape(topStart = SheetCorner, bottomStart = SheetCorner)
                    } else {
                        RoundedCornerShape(topEnd = SheetCorner, bottomEnd = SheetCorner)
                    },
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                tonalElevation = 1.dp,
                shadowElevation = 6.dp,
            ) {
                Column(modifier = Modifier.padding(24.dp), content = content)
            }
        }
    }
}

private val SheetCorner = 16.dp
