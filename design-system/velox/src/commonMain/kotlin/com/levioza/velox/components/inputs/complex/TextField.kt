package com.levioza.velox.components.inputs.complex

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField as MaterialTextField

internal val veloxTextFieldShape = RoundedCornerShape(12.dp)

/**
 * Input text metrics shared by every Velox text input.
 *
 * The line box is trimmed to the glyphs. With Material's default trim an empty field keeps the
 * font's extra leading and measures taller than the same field with text in it, so the field
 * changes height on the first keystroke and back again when the text is cleared.
 */
@Composable
internal fun veloxTextFieldTextStyle(): TextStyle =
    MaterialTheme.typography.bodyLarge.copy(
        lineHeightStyle =
            LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.Both,
            ),
    )

@Composable
internal fun veloxTextFieldColors(): TextFieldColors =
    OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.72f),
        disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.28f),
    )

/**
 * Renders the Velox text field component.
 *
 * @param value Current controlled value.
 * @param onValueChange Callback invoked with a requested value change.
 * @param modifier Modifier applied to the component.
 * @param label Label text or label provider for the component.
 * @param placeholder Placeholder shown when no value is present.
 * @param supportingText Optional supporting text.
 * @param enabled Whether user interaction is enabled.
 * @param readOnly Whether the value can be selected but not edited.
 * @param isError Whether the component is displaying an error state.
 * @param singleLine Whether input is constrained to a single line.
 * @param outlined Whether to use the outlined text-field style.
 * @param leadingIcon Optional icon displayed before the main content.
 * @param trailingIcon Optional icon displayed after the main content.
 */
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    supportingText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true,
    outlined: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val labelContent: (@Composable () -> Unit)? = label?.let { text -> { Text(text) } }
    val placeholderContent: (@Composable () -> Unit)? = placeholder?.let { text -> { Text(text) } }
    val supportingContent: (@Composable () -> Unit)? = supportingText?.let { text -> { Text(text) } }
    if (outlined) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            label = labelContent,
            placeholder = placeholderContent,
            supportingText = supportingContent,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = singleLine,
            textStyle = veloxTextFieldTextStyle(),
            shape = veloxTextFieldShape,
            colors = veloxTextFieldColors(),
        )
    } else {
        MaterialTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            label = labelContent,
            placeholder = placeholderContent,
            supportingText = supportingContent,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = singleLine,
            textStyle = veloxTextFieldTextStyle(),
        )
    }
}
