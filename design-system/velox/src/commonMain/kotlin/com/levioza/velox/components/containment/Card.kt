@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.components.containment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card as MaterialCard

/**
 * Defines the supported card variant values.
 */
enum class CardVariant { Filled, Elevated, Outlined }

/**
 * Renders the Velox card component.
 *
 * @param modifier Modifier applied to the component.
 * @param variant Visual variant of the component.
 * @param onClick Callback invoked when the component is activated.
 * @param content Content rendered inside the component.
 */
@Composable
fun Card(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.Filled,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val body: @Composable ColumnScope.() -> Unit = {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
    when (variant) {
        CardVariant.Filled -> {
            if (onClick == null) MaterialCard(modifier = modifier, content = body)
            else MaterialCard(onClick = onClick, modifier = modifier, content = body)
        }
        CardVariant.Elevated -> {
            if (onClick == null) {
                MaterialCard(
                    modifier = modifier,
                    elevation = CardDefaults.elevatedCardElevation(),
                    content = body,
                )
            } else {
                MaterialCard(
                    onClick = onClick,
                    modifier = modifier,
                    elevation = CardDefaults.elevatedCardElevation(),
                    content = body,
                )
            }
        }
        CardVariant.Outlined -> {
            if (onClick == null) OutlinedCard(modifier = modifier, content = body)
            else OutlinedCard(onClick = onClick, modifier = modifier, content = body)
        }
    }
}
