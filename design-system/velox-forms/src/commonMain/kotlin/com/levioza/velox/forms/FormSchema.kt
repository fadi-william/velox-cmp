package com.levioza.velox.forms

/**
 * Identifies one field of a [FormSchema], carrying its initial value and its rules.
 *
 * Field keys are declared once on a schema object and then used to read values, bind controls, and
 * write cross-field rules, so a typo cannot address a field that does not exist.
 *
 * @param name Stable name of the field, used for error reporting and debugging.
 * @param initialValue Value the field holds before the user edits it, and after a reset.
 * @param rules Validations applied to the field, in declaration order.
 * @param dependencies Fields whose value this field's rules read.
 */
class FieldKey<T> internal constructor(
    val name: String,
    val initialValue: T,
    internal val rules: List<ValidationRule<T>>,
    internal val dependencies: Set<FieldKey<*>>,
) {
    /** Whether any rule marks this field as required. */
    val isRequired: Boolean = rules.any { it.marksRequired }

    override fun toString(): String = "FieldKey($name)"
}

/**
 * One validation applied to a field value.
 *
 * @param marksRequired Whether the rule makes the field required, which controls the field's
 *   required marker in the UI.
 * @param validate Returns an error message when the value is invalid, or null when it passes.
 */
class ValidationRule<T> internal constructor(
    internal val marksRequired: Boolean,
    internal val validate: (value: T, values: FormValues) -> String?,
)

/**
 * Declares the fields of a form and the rules applied to them.
 *
 * Subclass it as an object and declare one property per field:
 *
 * ```kotlin
 * object SignUp : FormSchema() {
 *     val email = field("email", "") {
 *         required("Email is required")
 *         email()
 *     }
 *     val password = field("password", "") {
 *         required()
 *         minLength(8, "Use at least 8 characters")
 *     }
 *     val confirmPassword = field("confirmPassword", "") {
 *         matches(password, "Passwords must match")
 *     }
 * }
 * ```
 */
abstract class FormSchema {
    private val declaredFields = mutableListOf<FieldKey<*>>()

    /** Every field declared on this schema, in declaration order. */
    val fields: List<FieldKey<*>>
        get() = declaredFields.toList()

    /**
     * Declares a field.
     *
     * @param name Stable name of the field, used for error reporting and debugging.
     * @param initialValue Value the field holds before it is edited, and after a reset.
     * @param rules Rules applied to the field, declared with the [FieldRules] builder.
     */
    fun <T> field(
        name: String,
        initialValue: T,
        rules: FieldRules<T>.() -> Unit = {},
    ): FieldKey<T> {
        require(declaredFields.none { it.name == name }) {
            "Field \"$name\" is already declared on this schema"
        }
        val builder = FieldRules<T>().apply(rules)
        val key =
            FieldKey(
                name = name,
                initialValue = initialValue,
                rules = builder.rules,
                dependencies = builder.dependencies,
            )
        declaredFields += key
        return key
    }

    /** Fields whose rules read [key]'s value, and which are revalidated when it changes. */
    internal fun dependentsOf(key: FieldKey<*>): List<FieldKey<*>> =
        declaredFields.filter { key in it.dependencies }
}

/**
 * Builds the rules of a single field.
 *
 * Rules run in declaration order and the first failure wins, so declare `required` before rules
 * that assume a present value.
 */
class FieldRules<T> internal constructor() {
    internal val rules = mutableListOf<ValidationRule<T>>()
    internal val dependencies = mutableSetOf<FieldKey<*>>()

    /**
     * Adds a rule that reports [message] when [predicate] fails.
     *
     * @param message Error reported when the value does not satisfy the rule.
     * @param marksRequired Whether the rule makes the field required.
     * @param predicate Returns true when the value is acceptable.
     */
    fun check(
        message: String,
        marksRequired: Boolean = false,
        predicate: (value: T) -> Boolean,
    ) {
        rules += ValidationRule(marksRequired) { value, _ -> message.takeIf { !predicate(value) } }
    }

    /**
     * Adds a rule that can read the whole form, for validations that span fields.
     *
     * @param dependsOn Fields the rule reads, so they trigger a revalidation when they change.
     * @param validate Returns an error message when the value is invalid, or null when it passes.
     */
    fun rule(
        vararg dependsOn: FieldKey<*>,
        validate: (value: T, values: FormValues) -> String?,
    ) {
        dependencies += dependsOn
        rules += ValidationRule(marksRequired = false, validate = validate)
    }

    /** Adds a rule that marks the field required and reports [message] when it fails. */
    internal fun requireThat(message: String, predicate: (value: T) -> Boolean) {
        check(message, marksRequired = true, predicate = predicate)
    }
}
