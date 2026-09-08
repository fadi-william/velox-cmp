package com.levioza.velox.catalog.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.inputs.basic.Button
import com.levioza.velox.components.inputs.basic.ButtonVariant
import com.levioza.velox.components.inputs.complex.SelectOption
import com.levioza.velox.forms.FormSchema
import com.levioza.velox.forms.RevalidationMode
import com.levioza.velox.forms.ValidationMode
import com.levioza.velox.forms.components.FormCheckbox
import com.levioza.velox.forms.components.FormDateField
import com.levioza.velox.forms.components.FormSelect
import com.levioza.velox.forms.components.FormSlider
import com.levioza.velox.forms.components.FormSwitch
import com.levioza.velox.forms.components.FormTextField
import com.levioza.velox.forms.email
import com.levioza.velox.forms.isTrue
import com.levioza.velox.forms.minLength
import com.levioza.velox.forms.rememberForm
import com.levioza.velox.forms.required
import org.jetbrains.compose.storytale.story

val `SampleForm` by
    story(group = "Form") {
        val BookingForm = remember {
            object : FormSchema() {
                val fullName = field("fullName", "") {
                    required("Enter your full name")
                    minLength(3, "That looks too short")
                }
                val email = field("email", "") {
                    required("Email is required")
                    email()
                }
                val destination = field<String?>("destination", null) {
                    required("Choose a destination")
                }
                val departure = field("departure", "") { required("Pick a departure date") }
                val travellers = field("travellers", 2f)
                val newsletter = field("newsletter", false)
                val terms = field("terms", false) { isTrue("Accept the terms to continue") }
            }
        }
        val form =
            rememberForm(
                BookingForm,
                mode = ValidationMode.OnTouched,
                revalidationMode = RevalidationMode.OnChange,
            )
        var confirmation by remember { mutableStateOf<String?>(null) }
        val destinations =
            listOf(
                SelectOption("cai", "Cairo"),
                SelectOption("ist", "Istanbul"),
                SelectOption("lis", "Lisbon"),
            )
        val travellers = form[BookingForm.travellers]

        Column(
            modifier = Modifier.width(380.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Book a trip", style = MaterialTheme.typography.headlineSmall)

            FormTextField(form[BookingForm.fullName], label = "Full name")
            FormTextField(
                form[BookingForm.email],
                label = "Email",
                placeholder = "you@example.com",
                helperText = "We send the itinerary here",
            )
            FormSelect(form[BookingForm.destination], destinations, label = "Destination")
            FormDateField(form[BookingForm.departure], label = "Departure")
            FormSlider(
                travellers,
                label = "Travellers",
                helperText = travellers.value.toInt().toString(),
                valueRange = 1f..8f,
                steps = 6,
            )
            FormSwitch(form[BookingForm.newsletter], label = "Send me deals")
            FormCheckbox(form[BookingForm.terms], label = "I accept the terms")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    text = "Book",
                    onClick = {
                        form.submit { values ->
                            confirmation =
                                "Booked ${values[BookingForm.travellers].toInt()} seat(s) to " +
                                    "${values[BookingForm.destination]} on " +
                                    values[BookingForm.departure]
                        }
                    },
                )
                Button(
                    text = "Reset",
                    onClick = {
                        form.reset()
                        confirmation = null
                    },
                    variant = ButtonVariant.Outlined,
                )
                Button(
                    text = "Server error",
                    onClick = { form.setError(BookingForm.email, "Email already booked") },
                    variant = ButtonVariant.Text,
                )
            }

            Text(
                text = "dirty ${form.isDirty} · valid ${form.isValid} · submits ${form.submitCount}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            confirmation?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
        }
    }
