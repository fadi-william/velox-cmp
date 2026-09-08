package com.levioza.velox.forms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/** Read-only view of the values currently held by a form. */
@Stable
interface FormValues {
    /** Returns the value held for [key]. */
    operator fun <T> get(key: FieldKey<T>): T
}

/**
 * Holds the values, errors, and interaction state of one form.
 *
 * Create it with [rememberForm] and bind fields with [get].
 *
 * @param schema Schema describing the fields and their rules.
 * @param mode When fields are validated before the first submit.
 * @param revalidationMode When fields are revalidated after the first submit.
 */
@Stable
class FormState(
    private val schema: FormSchema,
    private val mode: ValidationMode = ValidationMode.OnTouched,
    private val revalidationMode: RevalidationMode = RevalidationMode.OnChange,
) {
    private val fieldValues = mutableStateMapOf<FieldKey<*>, Any?>()
    private val errors = mutableStateMapOf<FieldKey<*>, String>()
    private val touchedFields = mutableStateMapOf<FieldKey<*>, Boolean>()

    /** How many times a submit has been attempted, valid or not. */
    var submitCount by mutableStateOf(0)
        private set

    /** Whether an asynchronous submit is in flight. */
    var isSubmitting by mutableStateOf(false)
        private set

    init {
        schema.fields.forEach { fieldValues[it] = it.initialValue }
    }

    /** The values currently held by the form, as handed to rules and to a submit callback. */
    val values: FormValues =
        object : FormValues {
            @Suppress("UNCHECKED_CAST")
            override fun <T> get(key: FieldKey<T>): T = fieldValues[key] as T
        }

    /**
     * Returns everything needed to render [key]: its value, its error, and the callbacks that
     * report edits and focus back to the form.
     */
    operator fun <T> get(key: FieldKey<T>): FieldState<T> =
        FieldState(
            key = key,
            value = values[key],
            error = errorOf(key),
            isRequired = key.isRequired,
            isTouched = isTouched(key),
            isDirty = isDirty(key),
            onValueChange = { value -> setValue(key, value) },
            onFocusLost = { onFocusLost(key) },
        )

    /** Every error currently displayed, keyed by field. */
    val visibleErrors: Map<FieldKey<*>, String>
        get() = errors.toMap()

    /** Whether any field currently displays an error. */
    val hasErrors: Boolean
        get() = errors.isNotEmpty()

    /**
     * Whether every field passes its rules right now.
     *
     * This runs the rules rather than reading the displayed errors, so it is true before the first
     * validation as long as the values are acceptable. Reading it subscribes to every field.
     */
    val isValid: Boolean
        get() = schema.fields.all { firstError(it) == null }

    /** Whether any field differs from the value it started with. */
    val isDirty: Boolean
        get() = schema.fields.any { fieldValues[it] != it.initialValue }

    /** Whether [key] has lost focus at least once, or has been marked touched by a submit. */
    fun isTouched(key: FieldKey<*>): Boolean = touchedFields[key] == true

    /** Whether [key] differs from the value it started with. */
    fun isDirty(key: FieldKey<*>): Boolean = fieldValues[key] != key.initialValue

    /** Returns the error currently displayed for [key], or null when none is shown. */
    fun errorOf(key: FieldKey<*>): String? = errors[key]

    /**
     * Writes [value] to [key], then validates it when the current mode calls for it.
     *
     * @param key Field being edited.
     * @param value New value of the field.
     */
    fun <T> setValue(key: FieldKey<T>, value: T) {
        fieldValues[key] = value
        if (shouldValidateOnChange(key)) {
            validateField(key)
        }
        revalidateDependents(key)
    }

    /**
     * Marks [key] as blurred, then validates it when the current mode calls for it.
     *
     * @param key Field that lost focus.
     */
    fun onFocusLost(key: FieldKey<*>) {
        touchedFields[key] = true
        if (shouldValidateOnBlur()) {
            validateField(key)
        }
    }

    /**
     * Displays [message] on [key], for errors a server reports.
     *
     * The message stays until the field is validated again.
     */
    fun setError(key: FieldKey<*>, message: String) {
        errors[key] = message
    }

    /** Clears the displayed error of [key], or of every field when [key] is null. */
    fun clearErrors(key: FieldKey<*>? = null) {
        if (key == null) errors.clear() else errors.remove(key)
    }

    /**
     * Validates every field and displays all failures.
     *
     * @return Whether the form is valid.
     */
    fun validate(): Boolean {
        schema.fields.forEach { validateField(it) }
        return errors.isEmpty()
    }

    /**
     * Validates the form and hands the values to [onValid] when it passes.
     *
     * @param onValid Called with the form's values when every field is valid.
     * @return Whether the form was valid and [onValid] ran.
     */
    fun submit(onValid: (FormValues) -> Unit): Boolean {
        submitCount += 1
        schema.fields.forEach { touchedFields[it] = true }
        if (!validate()) return false
        onValid(values)
        return true
    }

    /**
     * Validates the form and awaits [onValid] when it passes, reporting progress through
     * [isSubmitting] so a submit button can show it.
     *
     * @param onValid Called with the form's values when every field is valid.
     * @return Whether the form was valid and [onValid] ran.
     */
    suspend fun submitAsync(onValid: suspend (FormValues) -> Unit): Boolean {
        submitCount += 1
        schema.fields.forEach { touchedFields[it] = true }
        if (!validate()) return false
        isSubmitting = true
        try {
            onValid(values)
        } finally {
            isSubmitting = false
        }
        return true
    }

    /**
     * Returns the form to its initial values, clearing errors and interaction state.
     *
     * @param values Values to start from, defaulting to the schema's initial values.
     */
    fun reset(values: Map<FieldKey<*>, Any?> = emptyMap()) {
        schema.fields.forEach { fieldValues[it] = values[it] ?: it.initialValue }
        errors.clear()
        touchedFields.clear()
        submitCount = 0
    }

    private fun validateField(key: FieldKey<*>) {
        val message = firstError(key)
        if (message == null) errors.remove(key) else errors[key] = message
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> firstError(key: FieldKey<T>): String? {
        val value = fieldValues[key] as T
        return key.rules.firstNotNullOfOrNull { it.validate(value, values) }
    }

    // A field that reads another field's value goes stale when that other field changes, so it is
    // revalidated too, but only once it is already showing feedback.
    private fun revalidateDependents(key: FieldKey<*>) {
        schema.dependentsOf(key).forEach { dependent ->
            if (shouldValidateOnChange(dependent)) {
                validateField(dependent)
            }
        }
    }

    private fun shouldValidateOnChange(key: FieldKey<*>): Boolean =
        if (submitCount > 0) {
            revalidationMode == RevalidationMode.OnChange
        } else {
            when (mode) {
                ValidationMode.OnChange, ValidationMode.All -> true
                ValidationMode.OnTouched -> isTouched(key)
                ValidationMode.OnBlur, ValidationMode.OnSubmit -> false
            }
        }

    private fun shouldValidateOnBlur(): Boolean =
        if (submitCount > 0) {
            revalidationMode == RevalidationMode.OnBlur
        } else {
            when (mode) {
                ValidationMode.OnBlur, ValidationMode.OnTouched, ValidationMode.All -> true
                ValidationMode.OnChange, ValidationMode.OnSubmit -> false
            }
        }
}

/**
 * Everything one field needs to render itself: its value, its error, and the callbacks that report
 * edits and focus back to the form.
 *
 * @param key Field this state belongs to.
 * @param value Current value of the field.
 * @param error Error to display, or null when the field shows none.
 * @param isRequired Whether the field's rules make it required.
 * @param isTouched Whether the field has lost focus at least once.
 * @param isDirty Whether the field differs from the value it started with.
 * @param onValueChange Reports an edit to the form.
 * @param onFocusLost Reports lost focus to the form.
 */
@Stable
class FieldState<T>(
    val key: FieldKey<T>,
    val value: T,
    val error: String?,
    val isRequired: Boolean,
    val isTouched: Boolean,
    val isDirty: Boolean,
    val onValueChange: (T) -> Unit,
    val onFocusLost: () -> Unit,
)

/**
 * Creates a [FormState] that survives recomposition.
 *
 * @param schema Schema describing the fields and their rules.
 * @param mode When fields are validated before the first submit.
 * @param revalidationMode When fields are revalidated after the first submit.
 */
@Composable
fun rememberForm(
    schema: FormSchema,
    mode: ValidationMode = ValidationMode.OnTouched,
    revalidationMode: RevalidationMode = RevalidationMode.OnChange,
): FormState = remember(schema, mode, revalidationMode) { FormState(schema, mode, revalidationMode) }
