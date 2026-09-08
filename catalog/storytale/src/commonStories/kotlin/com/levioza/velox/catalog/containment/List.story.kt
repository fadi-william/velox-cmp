@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.containment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.containment.List
import com.levioza.velox.components.containment.ListItem
import com.levioza.velox.components.containment.ListSubheader
import org.jetbrains.compose.storytale.story

@Composable
private fun MailIcon() {
    Icon(
        imageVector = Icons.Default.Email,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun ChevronIcon() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun Avatar(initial: String) {
    Box(
        modifier =
            Modifier.size(40.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initial,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

val `List` by
    story(group = "Containment") {
        val showSubheaders by parameter(true)
        val showSupportingText by parameter(true)
        val showLeadingContent by parameter(true)
        val showTrailingChevron by parameter(true)

        List(modifier = Modifier.width(360.dp).verticalScroll(rememberScrollState())) {
            if (showSubheaders) ListSubheader("Mail")
            ListItem(
                "Inbox",
                supportingText = "12 new messages".takeIf { showSupportingText },
                leadingContent = if (showLeadingContent) ({ MailIcon() }) else null,
                trailingContent = if (showTrailingChevron) ({ ChevronIcon() }) else null,
                onClick = {},
            )
            ListItem(
                "Drafts",
                supportingText = "3 drafts".takeIf { showSupportingText },
                leadingContent = if (showLeadingContent) ({ MailIcon() }) else null,
                trailingContent = if (showTrailingChevron) ({ ChevronIcon() }) else null,
                onClick = {},
            )

            if (showSubheaders) ListSubheader("People")
            ListItem(
                "Jane Smith",
                supportingText = "jane@example.com".takeIf { showSupportingText },
                leadingContent = if (showLeadingContent) ({ Avatar("J") }) else null,
                trailingContent = if (showTrailingChevron) ({ ChevronIcon() }) else null,
                onClick = {},
            )
            ListItem(
                "Bob Johnson",
                overlineText = "AWAY",
                supportingText = "bob@example.com".takeIf { showSupportingText },
                leadingContent = if (showLeadingContent) ({ Avatar("B") }) else null,
                onClick = {},
            )
        }
    }
