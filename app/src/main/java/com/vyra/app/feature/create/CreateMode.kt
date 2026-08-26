package com.vyra.app.feature.create

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ContentCut
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector
import com.vyra.app.R

/**
 * The three ways to start a creation in VYRA. This is the app's core
 * information architecture, shared by the Home teasers and the Create hub.
 * Each mode's full flow is implemented in its own later phase.
 */
enum class CreateMode(
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    val icon: ImageVector,
) {
    AR(R.string.create_ar_title, R.string.create_ar_subtitle, Icons.Outlined.ViewInAr),
    CUTOUT(R.string.create_cutout_title, R.string.create_cutout_subtitle, Icons.Outlined.ContentCut),
    REMIX(R.string.create_remix_title, R.string.create_remix_subtitle, Icons.Outlined.AutoAwesome),
}
