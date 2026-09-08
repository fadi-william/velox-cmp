@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.runtime.remember
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormCheckbox
import com.levioza.velox.forms.isTrue
import com.levioza.velox.forms.rememberForm
import org.jetbrains.compose.storytale.story

private class TermsForm(isRequired: Boolean) : FormSchema() {
    val terms = field("terms", false) {
        if (isRequired) isTrue("Accept the terms to continue")
    }
}

val `FormCheckbox` by
    story(group = "Form") {
        val label by parameter("Accept terms")
        val helperText by parameter("Required to continue")
        val isRequired by parameter(true)
        val mode by parameter(ValidationMode.OnTouched)
        val enabled by parameter(true)

        val schema = remember(isRequired) { TermsForm(isRequired) }
        val form = rememberForm(schema, mode = mode)
        FormCheckbox(
            form[schema.terms],
            label = label,
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
        )
    }
