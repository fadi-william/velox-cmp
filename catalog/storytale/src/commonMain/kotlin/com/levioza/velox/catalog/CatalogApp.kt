package com.levioza.velox.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.theme.VeloxSpacing
import com.levioza.velox.theme.VeloxTheme

/** A lightweight host used by normal platform run configurations. Storytale tasks launch the full gallery. */
@Composable
fun CatalogApp() {
    VeloxTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(VeloxSpacing.extraLarge),
                verticalArrangement = Arrangement.spacedBy(VeloxSpacing.medium),
            ) {
                Text("Velox", style = androidx.compose.material3.MaterialTheme.typography.displaySmall)
                Text("A Compose Multiplatform design system built on Material 3.")
                Button(text = "Explore in Storytale", onClick = {})
            }
        }
    }
}
