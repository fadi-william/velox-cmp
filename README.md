# Velox Compose Multiplatform

A small monorepo for the Velox Material 3 design system and its Storytale component catalog.

## Modules

- `design-system:velox` — reusable Compose Multiplatform Material 3 theme, tokens, and components.
- `design-system:velox-forms` — form state, a validation schema, and Velox controls bound to it,
  plus `FormField` for framing a control that has no label of its own.
- `catalog:storytale` — Storytale stories plus ordinary Android, iOS, desktop, and Wasm hosts.
- `tooling:storytale-gallery` — a narrowly customized Storytale gallery UI used by every catalog target.

Storytale and Dokka solve different problems. Storytale is the visual workshop: component states, parameters, themes, and interaction. Dokka is the searchable contract for public Kotlin APIs and KDoc. Keep both, while keeping Dokka documentation focused on the published design-system module.

Storytale is currently an early, unstable JetBrains project. Its version is centralized in `gradle/libs.versions.toml` so it can be upgraded or temporarily pinned in one place.

The root `generateResourceAccessorsForCommonStories` task is a compatibility bridge for Storytale's current Android support in nested modules. Remove it when Storytale stops resolving that task from the root project.

Storytale does not yet expose gallery styling hooks, so the small Apache-2.0 gallery source override lives in `tooling/storytale-gallery`. Its search field is pill-shaped and uses placeholder text instead of a floating label.

## Forms

`velox-forms` holds form values, runs a schema of rules, and decides when an error is shown. Declare
the fields once, then bind controls to them:

```kotlin
object SignUp : FormSchema() {
    val email = field("email", "") {
        required("Email is required")
        email()
    }
    val password = field("password", "") {
        required()
        minLength(8, "Use at least 8 characters")
    }
    val confirmPassword = field("confirmPassword", "") {
        matches(password, "Passwords must match")
    }
    val terms = field("terms", false) { isTrue("Accept the terms") }
}

@Composable
fun SignUpScreen(onSignUp: (String) -> Unit) {
    val form = rememberForm(SignUp, mode = ValidationMode.OnTouched)

    FormTextField(form[SignUp.email], label = "Email")
    FormTextField(form[SignUp.password], label = "Password")
    FormTextField(form[SignUp.confirmPassword], label = "Confirm password")
    FormCheckbox(form[SignUp.terms], label = "I accept the terms")

    Button("Create account", onClick = { form.submit { onSignUp(it[SignUp.email]) } })
}
```

`ValidationMode` decides when errors first appear — `OnSubmit`, `OnBlur`, `OnChange`, `OnTouched`,
or `All` — and `RevalidationMode` decides when they are rechecked after the first submit, which is
what clears an error while the user fixes a field. A field's error is cleared as soon as it passes.

`FormState` also exposes `isValid`, `isDirty`, `submitCount`, `isSubmitting`, `setError` for errors
a server reports, `reset`, and `submitAsync` for a suspending submit.

## Clearing focus

Compose keeps focus until something else takes it, so tapping the space around a text field leaves
it looking and behaving as though it were still active, and a form never sees the field blur. Apply
`Modifier.clearFocusOnTap()` to a screen or form container to get the behaviour a web page has:

```kotlin
Column(modifier = Modifier.fillMaxSize().clearFocusOnTap()) {
    FormTextField(form[SignUp.email], label = "Email")
}
```

Taps consumed by a child, such as the field itself, never reach the modifier, so focus still moves
between fields normally.

## Requirements

- JDK 17 or 21
- Android SDK 36 for Android builds
- Xcode and an installed iOS simulator runtime for iOS

## Storytale gallery

The gallery opens on a gallery-owned Velox home page. Use the `Components`/`Home` button in the
top bar to switch between that page and the Storytale component browser; the home page is not a story.

```shell
# Desktop
./gradlew :catalog:storytale:desktopStoriesRun

# Web (Wasm dev server)
./gradlew :catalog:storytale:wasmJsBrowserStoriesDevelopmentRun

# Android (connected device or configured emulator)
./gradlew :catalog:storytale:androidStoriesRun

# iOS simulator on Apple Silicon
./gradlew :catalog:storytale:iosSimulatorArm64StoriesRun
```

Run `./gradlew :catalog:storytale:tasks --group storytale` to see the exact generated tasks available on the current host.

## API reference

```shell
./gradlew docs
```

The generated HTML is under `design-system/velox/build/dokka/html`.

## Verification

```shell
./gradlew :design-system:velox:allTests
./gradlew :catalog:storytale:compileStoriesKotlinDesktop
./gradlew :catalog:storytale:wasmJsBrowserStoriesDevelopmentWebpack
```
