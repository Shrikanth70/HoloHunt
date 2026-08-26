package com.vyra.app.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typeface for VYRA.
 *
 * DESIGN.md specifies **Inter** for every text style. The Inter font files are
 * not yet bundled in the project, so this is intentionally a temporary stand-in
 * mapped to the platform sans-serif. Because every [TextStyle] below already
 * encodes Inter's exact size / weight / line-height / tracking, the visual
 * hierarchy is faithful now, and swapping in the real typeface later is a
 * one-line change here with zero impact on call sites.
 *
 * TODO(Phase 10): bundle Inter (or wire the Google Fonts provider) and set this
 * to the Inter [FontFamily].
 */
internal val VyraFontFamily = FontFamily.SansSerif

/** Type scale transcribed from the `DESIGN.md` typography tokens. */
internal val VyraTypography = Typography(
    // display-lg: 44 / 52, 700, -0.02em
    displayLarge = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 44.sp,
        lineHeight = 52.sp,
        letterSpacing = (-0.88).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.5).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 38.sp,
    ),
    // headline-lg: 32 / 40, 600, -0.01em
    headlineLarge = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.32).sp,
    ),
    // headline-md: 24 / 32, 600
    headlineMedium = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    // body-lg: 18 / 28, 400
    bodyLarge = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
    ),
    // body-md: 16 / 24, 400
    bodyMedium = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    // label-md (14 / 20, 500, 0.05em) reused for the "large" label / button text.
    labelLarge = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.7.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.7.sp,
    ),
    // label-sm: 12 / 16, 600, 0.1em
    labelSmall = TextStyle(
        fontFamily = VyraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.2.sp,
    ),
)
