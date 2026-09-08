@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.components.FormSlider
import com.levioza.velox.forms.rememberForm
import org.jetbrains.compose.storytale.story

private class VolumeForm(initialValue: Float) : FormSchema() {
    val volume = field("volume", initialValue)
}

val `FormSlider` by
    story(group = "Form") {
        val label by parameter("Volume")
        val initialValue by parameter(0.6f)
        val steps by parameter(0)
        val enabled by parameter(true)

        val schema = remember(initialValue) { VolumeForm(initialValue) }
        val form = rememberForm(schema)
        val field = form[schema.volume]
        FormSlider(
            field,
            label = label,
            modifier = Modifier.width(360.dp),
            enabled = enabled,
            steps = steps,
            helperText = "${(field.value * 100).toInt()}%",
        )
    }
