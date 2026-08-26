package com.vyra.app.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Corner-radius scale from `DESIGN.md` ("Hyper-Rounded" language):
 * sm 4 · default 8 · md 12 · lg 16 · xl 24. Pill shapes (`full`) are applied
 * ad hoc via [androidx.compose.foundation.shape.CircleShape] on chips/status.
 */
internal val VyraShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
)
