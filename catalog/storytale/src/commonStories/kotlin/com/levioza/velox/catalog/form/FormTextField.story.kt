@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormTextField
import com.levioza.velox.forms.minLength
import com.levioza.velox.forms.rememberForm
import com.levioza.velox.forms.required
import org.jetbrains.compose.storytale.story

private class NameForm(isRequired: Boolean, minimumLength: Int) : FormSchema() {
    val name = field("name", "") {
        if (isRequired) required("Enter your full name")
        if (minimumLength > 0) minLength(minimumLength)
    }
}

val `FormTextField` by
    story(group = "Form") {
        val label by parameter("Name")
        val placeholder by parameter("")
        val helperText by parameter("Enter your full name")
        val isRequired by parameter(true)
        val minimumLength by parameter(3)
        val mode by parameter(ValidationMode.OnTouched)
        val enabled by parameter(true)

        val schema = remember(isRequired, minimumLength) { NameForm(isRequired, minimumLength) }
        val form = rememberForm(schema, mode = mode)
        FormTextField(
            form[schema.name],
            label = label,
            modifier = Modifier.width(360.dp),
            placeholder = placeholder.takeIf { it.isNotBlank() },
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
        )
    }
