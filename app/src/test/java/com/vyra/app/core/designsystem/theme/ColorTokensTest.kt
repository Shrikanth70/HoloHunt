package com.vyra.app.core.designsystem.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Guards the brand palette against accidental drift. These are the exact hex
 * values in `DESIGN.md`; if a token here changes, it must change there too.
 */
class ColorTokensTest {

    @Test
    fun surface_isDarkChrome() {
        assertEquals(Color(0xFF0D1515), VyraSurface)
        assertEquals(VyraSurface, VyraBackground)
    }

    @Test
    fun primaryContainer_isElectricCyan() {
        assertEquals(Color(0xFF00F0FF), VyraPrimaryContainer)
    }

    @Test
    fun primary_isPaleCyan() {
        assertEquals(Color(0xFFDBFCFF), VyraPrimary)
    }

    @Test
    fun actionCyan_matchesPrimaryContainerRole() {
        assertEquals(VyraPrimaryContainer, VyraActionCyan)
        assertEquals(VyraActionCyan, VyraGlow)
    }

    @Test
    fun onSurface_isCoolWhite() {
        assertEquals(Color(0xFFDCE4E5), VyraOnSurface)
    }
}
