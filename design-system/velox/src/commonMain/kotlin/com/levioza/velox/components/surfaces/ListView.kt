package com.levioza.velox.components.surfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Renders typed items in a vertically scrolling list.
 *
 * @param items Items rendered by the component.
 * @param modifier Modifier applied to the component.
 * @param contentPadding Padding applied around the component content.
 * @param verticalSpacing Vertical spacing between items.
 * @param key Optional stable key provider for list items.
 * @param itemContent Composable used to render each item.
 */
@Composable
fun <T> ListView(
    items: List<T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalSpacing: Dp = 0.dp,
    key: ((T) -> Any)? = null,
    itemContent: @Composable (T) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
    ) {
        items(
            items = items,
            key = if (key == null) null else { item -> key(item) },
        ) { item ->
            itemContent(item)
        }
    }
}
