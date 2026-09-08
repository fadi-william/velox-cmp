@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.surfaces.Collapsible
import org.jetbrains.compose.storytale.story

val `Collapsible` by
    story(group = "Surfaces & Search") {
        val title by parameter("Advanced options")
        val body by parameter("Fine-tune the component with additional controls.")
        val initiallyExpanded by parameter(true)

        var expanded by remember(initiallyExpanded) { mutableStateOf(initiallyExpanded) }

        // The header is the toggle, so it keeps its place while the body opens and closes. The
        // reserved height stops the story from being re-centred as the body grows.
        Column(Modifier.width(360.dp).height(200.dp)) {
            Collapsible(
                expanded = expanded,
                header = {
                    Row(
                        modifier =
                            Modifier.fillMaxWidth()
                                // Clipped first so the press and hover state layer follows the
                                // rounded header rather than washing over a bare rectangle.
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { expanded = !expanded }
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(title, style = MaterialTheme.typography.titleMedium)
                        Icon(
                            imageVector =
                                if (expanded) {
                                    Icons.Default.KeyboardArrowUp
                                } else {
                                    Icons.Default.KeyboardArrowDown
                                },
                            contentDescription = if (expanded) "Collapse" else "Expand",
                        )
                    }
                },
            ) {
                Text(
                    text = body,
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
