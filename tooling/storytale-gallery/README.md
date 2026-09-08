# Velox Storytale gallery override

This module vendors JetBrains Storytale's Apache-2.0 gallery source at the version pinned in
`gradle/libs.versions.toml`. It exists because Storytale does not currently expose gallery UI
styling hooks. Keep local changes small so the source can be refreshed from upstream easily.

Velox customization: the story search field uses a pill shape and placeholder instead of a
floating label. The gallery shell also owns a welcome home page and a Home/Components toggle;
the welcome content is not registered as a story.
