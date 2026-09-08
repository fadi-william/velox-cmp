@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.runtime.remember
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormRadioGroup
import com.levioza.velox.forms.rememberForm
import com.levioza.velox.forms.required
import org.jetbrains.compose.storytale.story

private class PlanForm(isRequired: Boolean) : FormSchema() {
    val plan = field<String?>("plan", null) {
        if (isRequired) required("Pick a plan")
    }
}

val `FormRadioGroup` by
    story(group = "Form") {
        val label by parameter("Plan")
        val optionLabels by parameter("Starter, Pro, Team")
        val helperText by parameter("")
        val isRequired by parameter(true)
        val mode by parameter(ValidationMode.OnTouched)

        val schema = remember(isRequired) { PlanForm(isRequired) }
        val form = rememberForm(schema, mode = mode)
        FormRadioGroup(
            form[schema.plan],
            options = optionLabels.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            optionLabel = { it },
            label = label,
            helperText = helperText.takeIf { it.isNotBlank() },
        )
    }
