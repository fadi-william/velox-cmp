package com.levioza.velox.components.surfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Lays out card content in an adaptive grid.
 *
 * @param modifier Modifier applied to the component.
 * @param minimumCardWidth Minimum width used when calculating adaptive grid columns.
 * @param contentPadding Padding applied around the component content.
 * @param horizontalSpacing Horizontal spacing between grid items.
 * @param verticalSpacing Vertical spacing between items.
 * @param content Content rendered inside the component.
 */
@Composable
fun CardGrid(
    modifier: Modifier = Modifier,
    minimumCardWidth: Dp = 260.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalSpacing: Dp = 16.dp,
    verticalSpacing: Dp = 16.dp,
    content: LazyGridScope.() -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = minimumCardWidth),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        content = content,
    )
}
