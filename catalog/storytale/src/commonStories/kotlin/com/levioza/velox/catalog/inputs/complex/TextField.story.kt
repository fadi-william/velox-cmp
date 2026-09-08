@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.inputs.complex

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.complex.TextField
import org.jetbrains.compose.storytale.story

val `TextField` by
    story(group = "Complex Inputs") {
        val label by parameter("Email")
        val placeholder by parameter("name@example.com")
        val supportingText by parameter("We will never share your email")
        val enabled by parameter(true)
        val readOnly by parameter(false)
        val isError by parameter(false)
        val singleLine by parameter(true)
        val outlined by parameter(true)

        var value by remember { mutableStateOf("") }
        TextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(360.dp),
            label = label,
            placeholder = placeholder,
            supportingText = supportingText,
            enabled = enabled,
            readOnly = readOnly,
            isError = isError,
            singleLine = singleLine,
            outlined = outlined,
        )
    }
