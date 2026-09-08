@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.surfaces

import androidx.compose.material3.Text
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.inputs.basic.ButtonVariant
import com.levioza.velox.components.surfaces.FormSheetScaffold
import org.jetbrains.compose.storytale.story

val `FormSheetScaffold` by
    story(group = "Surfaces & Search") {
        val title by parameter("Create account")
        val description by parameter("Tell us a little about yourself")
        val confirmLabel by parameter("Save")
        val dismissLabel by parameter("Cancel")

        FormSheetScaffold(
            title = title,
            description = description.takeIf { it.isNotBlank() },
            actions = {
                if (dismissLabel.isNotBlank()) {
                    Button(dismissLabel, {}, variant = ButtonVariant.Text)
                }
                Button(confirmLabel, {})
            },
        ) {
            Text("Form content")
        }
    }
