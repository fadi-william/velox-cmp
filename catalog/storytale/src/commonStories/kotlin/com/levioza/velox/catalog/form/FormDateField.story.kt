@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormDateField
import com.levioza.velox.forms.rememberForm
import com.levioza.velox.forms.required
import org.jetbrains.compose.storytale.story

private class StartDateForm(initialValue: String, isRequired: Boolean) : FormSchema() {
    val startDate = field("startDate", initialValue) {
        if (isRequired) required("Pick a start date")
    }
}

val `FormDateField` by
    story(group = "Form") {
        val label by parameter("Start date")
        val initialValue by parameter("2026-09-08")
        val helperText by parameter("")
        val isRequired by parameter(true)
        val mode by parameter(ValidationMode.OnTouched)
        val enabled by parameter(true)

        val schema = remember(initialValue, isRequired) { StartDateForm(initialValue, isRequired) }
        val form = rememberForm(schema, mode = mode)
        FormDateField(
            form[schema.startDate],
            label = label,
            modifier = Modifier.width(360.dp),
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
        )
    }
