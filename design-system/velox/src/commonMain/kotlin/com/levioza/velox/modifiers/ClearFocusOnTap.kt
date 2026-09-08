package com.levioza.velox.modifiers

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Clears focus when a tap lands on this element rather than on a focusable child.
 *
 * Compose keeps focus until something else takes it, so a text field stays active after a tap on
 * empty space: the label stays raised, the caret keeps blinking, and a form never sees the field
 * lose focus. Apply this to a screen or form container so tapping the background behaves the way
 * it does on the web.
 *
 * Taps consumed by a child, such as the text field itself, never reach this modifier, so a field
 * still takes focus normally.
 */
fun Modifier.clearFocusOnTap(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    pointerInput(focusManager) { detectTapGestures { focusManager.clearFocus() } }
}
