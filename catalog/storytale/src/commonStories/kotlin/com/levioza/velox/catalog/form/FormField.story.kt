@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.Slider
import com.levioza.velox.forms.components.FormField
import com.levioza.velox.forms.components.FormFieldLabelPlacement
import org.jetbrains.compose.storytale.story

// FormField supplies the label, the required marker, and the helper or error line for controls
// that have none of their own, such as this slider.
val `FormField` by
    story(group = "Form") {
        val label by parameter("Volume")
        val helperText by parameter("Drag to set the level")
        val errorMessage by parameter("")
        val required by parameter(true)
        val labelPlacement by parameter(FormFieldLabelPlacement.Above)

        var volume by remember { mutableFloatStateOf(0.6f) }
        FormField(
            label = label,
            modifier = Modifier.width(360.dp),
            required = required,
            helperText = helperText.takeIf { it.isNotBlank() },
            errorMessage = errorMessage.takeIf { it.isNotBlank() },
            labelPlacement = labelPlacement,
        ) {
            Slider(volume, { volume = it }, Modifier.fillMaxWidth())
        }
    }
