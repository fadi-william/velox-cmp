@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.forms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Defines where a form field label is positioned relative to its control. */
enum class FormFieldLabelPlacement {
    /** Places the label above the control. */
    Above,

    /** Places the label beside the control. */
    Beside,
}

/**
 * Provides shared label, helper, and validation layout for form controls.
 *
 * @param label Label text or label provider for the component.
 * @param modifier Modifier applied to the component.
 * @param required Whether the field is marked as required.
 * @param helperText Optional guidance displayed below the field.
 * @param errorMessage Optional validation error displayed below the field.
 * @param labelPlacement Position of the label relative to the control.
 * @param content Content rendered inside the component.
 */
@Composable
fun FormField(
    label: String,
    modifier: Modifier = Modifier,
    required: Boolean = false,
    helperText: String? = null,
    errorMessage: String? = null,
    labelPlacement: FormFieldLabelPlacement = FormFieldLabelPlacement.Above,
    content: @Composable () -> Unit,
) {
    val labelContent = @Composable {
        Text(
            text = if (required) "$label *" else label,
            style = MaterialTheme.typography.labelLarge,
            color =
                if (errorMessage == null) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.error
                },
        )
    }
    // The line is always drawn, empty when there is nothing to say: a field that gains an error
    // mid-edit would otherwise grow and shift everything below it.
    val supportingContent = @Composable {
        Text(
            text = errorMessage ?: helperText.orEmpty(),
            style = MaterialTheme.typography.bodySmall,
            color =
                if (errorMessage == null) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.error
                },
        )
    }

    if (labelPlacement == FormFieldLabelPlacement.Above) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            labelContent()
            content()
            supportingContent()
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
        ) {
            content()
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                labelContent()
                supportingContent()
            }
        }
    }
}
