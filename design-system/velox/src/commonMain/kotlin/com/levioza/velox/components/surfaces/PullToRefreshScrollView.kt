package com.levioza.velox.components.surfaces

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Hosts scrollable content with Material 3 pull-to-refresh behavior.
 *
 * @param isRefreshing Value used to configure the component.
 * @param onRefresh Callback invoked when a refresh gesture is completed.
 * @param modifier Modifier applied to the component.
 * @param content Content rendered inside the component.
 */
@Composable
fun PullToRefreshScrollView(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        content = content,
    )
}
