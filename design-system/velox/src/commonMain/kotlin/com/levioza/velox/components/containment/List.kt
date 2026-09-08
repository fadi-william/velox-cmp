@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.containment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ListItem as MaterialListItem

/**
 * Renders the Velox list component.
 *
 * @param modifier Modifier applied to the component.
 * @param content Content rendered inside the component.
 */
@Composable
fun List(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier, content = content)
}

/**
 * Renders the Velox list item component.
 *
 * @param headline Primary text displayed by the list item.
 * @param modifier Modifier applied to the component.
 * @param supportingText Optional supporting text.
 * @param overlineText Optional overline displayed above the headline.
 * @param leadingContent Optional content displayed before the list text.
 * @param trailingContent Optional content displayed after the list text.
 * @param onClick Callback invoked when the component is activated.
 */
@Composable
fun ListItem(
    headline: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    overlineText: String? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    MaterialListItem(
        headlineContent = { Text(headline) },
        modifier = if (onClick == null) modifier else modifier.clickable(onClick = onClick),
        supportingContent = supportingText?.let { value -> { Text(value) } },
        overlineContent = overlineText?.let { value -> { Text(value) } },
        leadingContent = leadingContent,
        trailingContent = trailingContent,
    )
}

/**
 * Renders the Velox list subheader component.
 *
 * @param text Text displayed by the component.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun ListSubheader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
    )
}
