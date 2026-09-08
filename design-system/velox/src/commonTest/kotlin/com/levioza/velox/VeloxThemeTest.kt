package com.levioza.velox

import com.levioza.velox.theme.VeloxDarkColorScheme
import com.levioza.velox.theme.VeloxLightColorScheme
import kotlin.test.Test
import kotlin.test.assertNotEquals

class VeloxThemeTest {
    @Test
    fun lightAndDarkSchemesUseDifferentPrimaryColors() {
        assertNotEquals(VeloxLightColorScheme.primary, VeloxDarkColorScheme.primary)
    }
}
