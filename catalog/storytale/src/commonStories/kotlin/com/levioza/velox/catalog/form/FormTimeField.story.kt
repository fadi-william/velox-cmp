@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormTimeField
import com.levioza.velox.forms.rememberForm
import com.levioza.velox.forms.required
import org.jetbrains.compose.storytale.story

private class MeetingForm(initialValue: String, isRequired: Boolean) : FormSchema() {
    val time = field("time", initialValue) {
        if (isRequired) required("Pick a time")
    }
}

val `FormTimeField` by
    story(group = "Form") {
        val label by parameter("Meeting time")
        val initialValue by parameter("10:30")
        val helperText by parameter("")
        val isRequired by parameter(false)
        val mode by parameter(ValidationMode.OnTouched)
        val is24Hour by parameter(false)
        val enabled by parameter(true)

        val schema = remember(initialValue, isRequired) { MeetingForm(initialValue, isRequired) }
        val form = rememberForm(schema, mode = mode)
        FormTimeField(
            form[schema.time],
            label = label,
            modifier = Modifier.width(360.dp),
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
            is24Hour = is24Hour,
        )
    }
