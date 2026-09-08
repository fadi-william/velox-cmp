package com.levioza.velox.forms

/**
 * Decides when a field is validated before the form has been submitted once.
 *
 * A field shows an error only after it has been validated, so this is what decides when errors
 * first appear.
 */
enum class ValidationMode {
    /** Validates nothing until the form is submitted. */
    OnSubmit,

    /** Validates a field when it loses focus. */
    OnBlur,

    /** Validates a field on every edit. */
    OnChange,

    /** Validates a field when it first loses focus, then on every later edit. */
    OnTouched,

    /** Validates a field on every edit and whenever it loses focus. */
    All,
}

/**
 * Decides when a field is revalidated after the form has been submitted once.
 *
 * This is what clears an error while the user fixes a field, so the default revalidates on change.
 */
enum class RevalidationMode {
    /** Revalidates a field on every edit. */
    OnChange,

    /** Revalidates a field when it loses focus. */
    OnBlur,

    /** Revalidates only on the next submit. */
    OnSubmit,
}
