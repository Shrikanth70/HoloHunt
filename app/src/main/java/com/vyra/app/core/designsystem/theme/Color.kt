package com.vyra.app.core.designsystem.theme

import androidx.compose.ui.graphics.Color

/**
 * VYRA color tokens — the single source of truth, transcribed verbatim from
 * `DESIGN.md` (which uses Material 3 color-role names). Do not hand-pick colors
 * elsewhere in the app; consume these through [androidx.compose.material3.MaterialTheme].
 */

// Primary
internal val VyraPrimary = Color(0xFFDBFCFF)
internal val VyraOnPrimary = Color(0xFF00363A)
internal val VyraPrimaryContainer = Color(0xFF00F0FF)
internal val VyraOnPrimaryContainer = Color(0xFF006970)
internal val VyraInversePrimary = Color(0xFF006970)

// Secondary
internal val VyraSecondary = Color(0xFFC8C6C8)
internal val VyraOnSecondary = Color(0xFF303032)
internal val VyraSecondaryContainer = Color(0xFF474649)
internal val VyraOnSecondaryContainer = Color(0xFFB7B4B7)

// Tertiary
internal val VyraTertiary = Color(0xFFF8F5F8)
internal val VyraOnTertiary = Color(0xFF303032)
internal val VyraTertiaryContainer = Color(0xFFDBD9DB)
internal val VyraOnTertiaryContainer = Color(0xFF5F5E61)

// Error
internal val VyraError = Color(0xFFFFB4AB)
internal val VyraOnError = Color(0xFF690005)
internal val VyraErrorContainer = Color(0xFF93000A)
internal val VyraOnErrorContainer = Color(0xFFFFDAD6)

// Background / surface
internal val VyraBackground = Color(0xFF0D1515)
internal val VyraOnBackground = Color(0xFFDCE4E5)
internal val VyraSurface = Color(0xFF0D1515)
internal val VyraOnSurface = Color(0xFFDCE4E5)
internal val VyraSurfaceVariant = Color(0xFF2E3637)
internal val VyraOnSurfaceVariant = Color(0xFFB9CACB)
internal val VyraSurfaceDim = Color(0xFF0D1515)
internal val VyraSurfaceBright = Color(0xFF333B3B)
internal val VyraSurfaceContainerLowest = Color(0xFF080F10)
internal val VyraSurfaceContainerLow = Color(0xFF151D1E)
internal val VyraSurfaceContainer = Color(0xFF192122)
internal val VyraSurfaceContainerHigh = Color(0xFF232B2C)
internal val VyraSurfaceContainerHighest = Color(0xFF2E3637)

// Outline / inverse / tint
internal val VyraOutline = Color(0xFF849495)
internal val VyraOutlineVariant = Color(0xFF3B494B)
internal val VyraInverseSurface = Color(0xFFDCE4E5)
internal val VyraInverseOnSurface = Color(0xFF2A3233)
internal val VyraSurfaceTint = Color(0xFF00DBE9)
internal val VyraScrim = Color(0xFF000000)

/**
 * VYRA semantic extras that have no direct M3 role but recur in `DESIGN.md`:
 * the electric-cyan action color, its on-color (near-black), and the glow hue.
 */
val VyraActionCyan = Color(0xFF00F0FF)
val VyraOnAction = Color(0xFF002022)
val VyraGlow = Color(0xFF00F0FF)
