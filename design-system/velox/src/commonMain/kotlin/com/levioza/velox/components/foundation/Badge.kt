package com.levioza.velox.components.foundation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Badge as MaterialBadge

/**
 * Defines the supported badge size values.
 */
enum class BadgeSize { Small, Large }

/**
 * Renders the Velox badge component.
 *
 * @param modifier Modifier applied to the component.
 * @param text Text displayed by the component.
 * @param size Visual size of the component.
 */
@Composable
fun Badge(
    modifier: Modifier = Modifier,
    text: String? = null,
    size: BadgeSize = if (text == null) BadgeSize.Small else BadgeSize.Large,
) {
    MaterialBadge(
        modifier = modifier.then(if (size == BadgeSize.Small) Modifier.size(6.dp) else Modifier),
    ) {
        if (size == BadgeSize.Large && text != null) Text(text)
    }
}
