package com.levioza.velox.forms

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

private object SignUp : FormSchema() {
    val email = field("email", "") {
        required("Email is required")
        email("Enter a valid email address")
    }
    val password = field("password", "") {
        required()
        minLength(8, "Use at least 8 characters")
    }
    val confirmPassword = field("confirmPassword", "") {
        matches(password, "Passwords must match")
    }
    val terms = field("terms", false) {
        isTrue("Accept the terms")
    }
}

class FormStateTest {
    @Test
    fun onSubmitModeShowsNoErrorsBeforeSubmitting() {
        val form = FormState(SignUp, mode = ValidationMode.OnSubmit)
        form.setValue(SignUp.email, "nope")
        assertNull(form.errorOf(SignUp.email))

        assertFalse(form.submit {})
        assertEquals("Enter a valid email address", form.errorOf(SignUp.email))
    }

    @Test
    fun onChangeModeShowsErrorsWhileTyping() {
        val form = FormState(SignUp, mode = ValidationMode.OnChange)
        form.setValue(SignUp.email, "nope")
        assertEquals("Enter a valid email address", form.errorOf(SignUp.email))

        form.setValue(SignUp.email, "someone@example.com")
        assertNull(form.errorOf(SignUp.email))
    }

    @Test
    fun onTouchedModeWaitsForTheFirstBlurThenTracksEdits() {
        val form = FormState(SignUp, mode = ValidationMode.OnTouched)
        // Typing alone does not surface an error yet.
        form.setValue(SignUp.email, "nope")
        assertNull(form.errorOf(SignUp.email))

        form.onFocusLost(SignUp.email)
        assertEquals("Enter a valid email address", form.errorOf(SignUp.email))

        // Once touched, later edits revalidate and clear the error as soon as it passes.
        form.setValue(SignUp.email, "someone@example.com")
        assertNull(form.errorOf(SignUp.email))
    }

    @Test
    fun onBlurModeIgnoresEditsUntilFocusIsLost() {
        val form = FormState(SignUp, mode = ValidationMode.OnBlur)
        form.setValue(SignUp.email, "nope")
        assertNull(form.errorOf(SignUp.email))

        form.onFocusLost(SignUp.email)
        assertEquals("Enter a valid email address", form.errorOf(SignUp.email))
    }

    @Test
    fun revalidationOnChangeClearsErrorsAfterAFailedSubmit() {
        val form =
            FormState(
                SignUp,
                mode = ValidationMode.OnSubmit,
                revalidationMode = RevalidationMode.OnChange,
            )
        assertFalse(form.submit {})
        assertEquals("Email is required", form.errorOf(SignUp.email))

        form.setValue(SignUp.email, "someone@example.com")
        assertNull(form.errorOf(SignUp.email))
    }

    @Test
    fun revalidationOnSubmitKeepsErrorsWhileTyping() {
        val form =
            FormState(
                SignUp,
                mode = ValidationMode.OnSubmit,
                revalidationMode = RevalidationMode.OnSubmit,
            )
        assertFalse(form.submit {})
        form.setValue(SignUp.email, "someone@example.com")
        assertEquals("Email is required", form.errorOf(SignUp.email))

        form.setValue(SignUp.password, "longenough")
        form.setValue(SignUp.confirmPassword, "longenough")
        form.setValue(SignUp.terms, true)
        assertTrue(form.submit {})
        assertNull(form.errorOf(SignUp.email))
    }

    @Test
    fun rulesRunInOrderAndTheFirstFailureWins() {
        val form = FormState(SignUp, mode = ValidationMode.OnChange)
        form.setValue(SignUp.email, "")
        assertEquals("Email is required", form.errorOf(SignUp.email))

        form.setValue(SignUp.email, "nope")
        assertEquals("Enter a valid email address", form.errorOf(SignUp.email))
    }

    @Test
    fun changingAFieldRevalidatesTheFieldsThatReadIt() {
        val form = FormState(SignUp, mode = ValidationMode.OnChange)
        form.setValue(SignUp.password, "longenough")
        form.setValue(SignUp.confirmPassword, "longenough")
        assertNull(form.errorOf(SignUp.confirmPassword))

        // The confirmation goes stale when the password changes underneath it.
        form.setValue(SignUp.password, "different")
        assertEquals("Passwords must match", form.errorOf(SignUp.confirmPassword))
    }

    @Test
    fun submitPassesTheValuesWhenEveryFieldIsValid() {
        val form = FormState(SignUp)
        form.setValue(SignUp.email, "someone@example.com")
        form.setValue(SignUp.password, "longenough")
        form.setValue(SignUp.confirmPassword, "longenough")
        form.setValue(SignUp.terms, true)

        var submitted: String? = null
        assertTrue(form.submit { values -> submitted = values[SignUp.email] })
        assertEquals("someone@example.com", submitted)
        assertEquals(1, form.submitCount)
    }

    @Test
    fun submitReportsInvalidWithoutCallingBack() {
        val form = FormState(SignUp)
        var called = false
        assertFalse(form.submit { called = true })
        assertFalse(called)
        assertEquals("Accept the terms", form.errorOf(SignUp.terms))
    }

    @Test
    fun serverErrorsStayUntilTheFieldIsValidatedAgain() {
        val form = FormState(SignUp, mode = ValidationMode.OnChange)
        form.setError(SignUp.email, "Email already in use")
        assertEquals("Email already in use", form.errorOf(SignUp.email))

        form.setValue(SignUp.email, "other@example.com")
        assertNull(form.errorOf(SignUp.email))
    }

    @Test
    fun requiredIsReportedFromTheRules() {
        assertTrue(SignUp.email.isRequired)
        assertTrue(SignUp.terms.isRequired)
        assertFalse(SignUp.confirmPassword.isRequired)
    }

    @Test
    fun validityAndDirtinessTrackTheValues() {
        val form = FormState(SignUp)
        assertFalse(form.isValid)
        assertFalse(form.isDirty)

        form.setValue(SignUp.email, "someone@example.com")
        assertTrue(form.isDirty)
        assertTrue(form.isDirty(SignUp.email))
        assertFalse(form.isDirty(SignUp.password))

        form.setValue(SignUp.password, "longenough")
        form.setValue(SignUp.confirmPassword, "longenough")
        form.setValue(SignUp.terms, true)
        assertTrue(form.isValid)
    }

    @Test
    fun resetReturnsToTheInitialState() {
        val form = FormState(SignUp, mode = ValidationMode.OnChange)
        form.setValue(SignUp.email, "nope")
        assertTrue(form.hasErrors)

        form.reset()
        assertEquals("", form.values[SignUp.email])
        assertFalse(form.hasErrors)
        assertFalse(form.isTouched(SignUp.email))
        assertFalse(form.isDirty)
        assertEquals(0, form.submitCount)
    }

    @Test
    fun resetCanStartFromGivenValues() {
        val form = FormState(SignUp)
        form.reset(mapOf(SignUp.email to "prefilled@example.com"))
        assertEquals("prefilled@example.com", form.values[SignUp.email])
        assertEquals("", form.values[SignUp.password])
    }

    @Test
    fun duplicateFieldNamesAreRejected() {
        val failure =
            runCatching {
                object : FormSchema() {
                    val first = field("name", "")
                    val second = field("name", "")
                }.fields
            }
        assertTrue(failure.isFailure)
    }
}
