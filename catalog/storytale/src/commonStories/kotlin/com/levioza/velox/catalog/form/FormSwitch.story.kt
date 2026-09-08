@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.form

import androidx.compose.runtime.remember
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.components.FormSwitch
import com.levioza.velox.forms.rememberForm
import org.jetbrains.compose.storytale.story

private class ProfileForm(initiallyPublic: Boolean) : FormSchema() {
    val isPublic = field("isPublic", initiallyPublic)
}

val `FormSwitch` by
    story(group = "Form") {
        val label by parameter("Public profile")
        val helperText by parameter("Anyone can view it")
        val initiallyOn by parameter(false)
        val enabled by parameter(true)

        val schema = remember(initiallyOn) { ProfileForm(initiallyOn) }
        val form = rememberForm(schema)
        FormSwitch(
            form[schema.isPublic],
            label = label,
            enabled = enabled,
            helperText = helperText.takeIf { it.isNotBlank() },
        )
    }
