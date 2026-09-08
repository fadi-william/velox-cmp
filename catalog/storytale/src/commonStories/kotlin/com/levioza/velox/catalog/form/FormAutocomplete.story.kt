@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.SelectOption
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormAutocomplete
import com.levioza.velox.forms.rememberForm
import com.levioza.velox.forms.required
import org.jetbrains.compose.storytale.story

private class CityForm(isRequired: Boolean) : FormSchema() {
    val city = field("city", "") {
        if (isRequired) required("Choose a city")
    }
}

val `FormAutocomplete` by
    story(group = "Form") {
        val label by parameter("City")
        val placeholder by parameter("Search")
        val optionLabels by parameter("Cairo, Giza, Lisbon, London, Tokyo")
        val helperText by parameter("")
        val isRequired by parameter(true)
        val mode by parameter(ValidationMode.OnTouched)
        val enabled by parameter(true)

        val schema = remember(isRequired) { CityForm(isRequired) }
        val form = rememberForm(schema, mode = mode)
        val field = form[schema.city]
        val options =
            optionLabels.split(",").map { it.trim() }.filter { it.isNotEmpty() }.map {
                SelectOption(it, it)
            }
        FormAutocomplete(
            field,
            options,
            label = label,
            modifier = Modifier.width(360.dp),
            placeholder = placeholder,
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
            onSelected = { value -> field.onValueChange(value.orEmpty()) },
        )
    }
