package com.vyra.app.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * VYRA is a dark-only, brand-fixed experience ("Dark Chrome / Neon Cyan"), so
 * there is a single [darkColorScheme] and no Material dynamic color. Every color
 * role is bound to a `DESIGN.md` token in [Color.kt].
 */
private val VyraColorScheme = darkColorScheme(
    primary = VyraPrimary,
    onPrimary = VyraOnPrimary,
    primaryContainer = VyraPrimaryContainer,
    onPrimaryContainer = VyraOnPrimaryContainer,
    inversePrimary = VyraInversePrimary,
    secondary = VyraSecondary,
    onSecondary = VyraOnSecondary,
    secondaryContainer = VyraSecondaryContainer,
    onSecondaryContainer = VyraOnSecondaryContainer,
    tertiary = VyraTertiary,
    onTertiary = VyraOnTertiary,
    tertiaryContainer = VyraTertiaryContainer,
    onTertiaryContainer = VyraOnTertiaryContainer,
    error = VyraError,
    onError = VyraOnError,
    errorContainer = VyraErrorContainer,
    onErrorContainer = VyraOnErrorContainer,
    background = VyraBackground,
    onBackground = VyraOnBackground,
    surface = VyraSurface,
    onSurface = VyraOnSurface,
    surfaceVariant = VyraSurfaceVariant,
    onSurfaceVariant = VyraOnSurfaceVariant,
    surfaceTint = VyraSurfaceTint,
    inverseSurface = VyraInverseSurface,
    inverseOnSurface = VyraInverseOnSurface,
    outline = VyraOutline,
    outlineVariant = VyraOutlineVariant,
    scrim = VyraScrim,
    surfaceDim = VyraSurfaceDim,
    surfaceBright = VyraSurfaceBright,
    surfaceContainerLowest = VyraSurfaceContainerLowest,
    surfaceContainerLow = VyraSurfaceContainerLow,
    surfaceContainer = VyraSurfaceContainer,
    surfaceContainerHigh = VyraSurfaceContainerHigh,
    surfaceContainerHighest = VyraSurfaceContainerHighest,
)

@Composable
fun VyraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VyraColorScheme,
        typography = VyraTypography,
        shapes = VyraShapes,
        content = content,
    )
}
