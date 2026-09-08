package com.levioza.velox.forms

import kotlin.jvm.JvmName

/** Reports [message] when the text is blank. */
fun FieldRules<String>.required(message: String = "This field is required") {
    requireThat(message) { it.isNotBlank() }
}

/** Reports [message] when a nullable value is absent. */
@JvmName("requiredNullable")
fun <T : Any> FieldRules<T?>.required(message: String = "This field is required") {
    requireThat(message) { it != null }
}

/** Reports [message] when the box is not ticked, for terms and similar gates. */
fun FieldRules<Boolean>.isTrue(message: String = "This field is required") {
    requireThat(message) { it }
}

/** Reports [message] when the text is shorter than [length]. */
fun FieldRules<String>.minLength(length: Int, message: String = "Use at least $length characters") {
    check(message) { it.isEmpty() || it.length >= length }
}

/** Reports [message] when the text is longer than [length]. */
fun FieldRules<String>.maxLength(length: Int, message: String = "Use at most $length characters") {
    check(message) { it.length <= length }
}

/** Reports [message] when the text does not match [regex]. */
fun FieldRules<String>.pattern(regex: Regex, message: String = "This value is not valid") {
    check(message) { it.isEmpty() || regex.matches(it) }
}

/** Reports [message] when the text does not look like an email address. */
fun FieldRules<String>.email(message: String = "Enter a valid email address") {
    pattern(EmailPattern, message)
}

/** Reports [message] when the number is below [minimum]. */
fun <T : Comparable<T>> FieldRules<T>.atLeast(minimum: T, message: String = "Enter $minimum or more") {
    check(message) { it >= minimum }
}

/** Reports [message] when the number is above [maximum]. */
fun <T : Comparable<T>> FieldRules<T>.atMost(maximum: T, message: String = "Enter $maximum or less") {
    check(message) { it <= maximum }
}

/** Reports [message] when a present number falls outside [minimum]..[maximum]. */
fun <T : Comparable<T>> FieldRules<T?>.inRange(
    minimum: T,
    maximum: T,
    message: String = "Enter a value between $minimum and $maximum",
) {
    check(message) { value -> value == null || (value >= minimum && value <= maximum) }
}

/** Reports [message] when the value is not one of [allowed]. */
fun <T> FieldRules<T>.oneOf(vararg allowed: T, message: String = "Choose one of the options") {
    check(message) { it in allowed }
}

/** Reports [message] when the value differs from [other]'s, for confirmation fields. */
fun <T> FieldRules<T>.matches(other: FieldKey<T>, message: String = "Values must match") {
    rule(other) { value, values -> message.takeIf { value != values[other] } }
}

private val EmailPattern = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$")
