@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.SelectOption
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormSelect
import com.levioza.velox.forms.rememberForm
import com.levioza.velox.forms.required
import org.jetbrains.compose.storytale.story

private class LanguageForm(isRequired: Boolean) : FormSchema() {
    val language = field<String?>("language", null) {
        if (isRequired) required("Choose a language")
    }
}

val `FormSelect` by
    story(group = "Form") {
        val label by parameter("Language")
        val placeholder by parameter("Select an option")
        val optionLabels by parameter("English, Arabic, French")
        val helperText by parameter("")
        val isRequired by parameter(true)
        val mode by parameter(ValidationMode.OnTouched)
        val enabled by parameter(true)

        val schema = remember(isRequired) { LanguageForm(isRequired) }
        val form = rememberForm(schema, mode = mode)
        val options =
            optionLabels.split(",").map { it.trim() }.filter { it.isNotEmpty() }.map {
                SelectOption(it, it)
            }
        FormSelect(
            form[schema.language],
            options,
            label = label,
            modifier = Modifier.width(360.dp),
            placeholder = placeholder,
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
        )
    }
