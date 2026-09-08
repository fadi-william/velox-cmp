@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.surfaces.PullToRefreshScrollView
import org.jetbrains.compose.storytale.story

val `PullToRefreshScrollView` by
    story(group = "Surfaces & Search") {
        val itemCount by parameter(6)
        val stayRefreshing by parameter(false)

        var refreshing by remember { mutableStateOf(false) }
        PullToRefreshScrollView(
            isRefreshing = refreshing,
            onRefresh = { refreshing = stayRefreshing },
            modifier = Modifier.size(320.dp),
        ) {
            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Pull to refresh")
                repeat(itemCount) { Text("Item ${it + 1}") }
            }
        }
    }
