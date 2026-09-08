@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.pickers.DateRangeValue
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.components.FormDateRangeField
import com.levioza.velox.forms.rememberForm
import org.jetbrains.compose.storytale.story

private class TripForm(start: String, end: String) : FormSchema() {
    val dates = field("dates", DateRangeValue(start, end))
}

val `FormDateRangeField` by
    story(group = "Form") {
        val label by parameter("Trip dates")
        val initialStart by parameter("2026-09-08")
        val initialEnd by parameter("2026-09-15")
        val helperText by parameter("")
        val enabled by parameter(true)

        val schema = remember(initialStart, initialEnd) { TripForm(initialStart, initialEnd) }
        val form = rememberForm(schema)
        FormDateRangeField(
            form[schema.dates],
            label = label,
            modifier = Modifier.width(360.dp),
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
        )
    }
