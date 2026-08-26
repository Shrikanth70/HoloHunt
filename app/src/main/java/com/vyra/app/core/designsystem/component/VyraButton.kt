package com.vyra.app.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vyra.app.core.designsystem.theme.VyraActionCyan
import com.vyra.app.core.designsystem.theme.VyraOnAction

/** The three button emphases in `DESIGN.md`. */
enum class VyraButtonStyle { Primary, Secondary, Ghost }

/**
 * VYRA's canonical button. [Primary][VyraButtonStyle.Primary] is the electric-cyan
 * filled CTA; [Secondary][VyraButtonStyle.Secondary] is an outlined action;
 * [Ghost][VyraButtonStyle.Ghost] is a low-emphasis text action. Height and radius
 * follow the design's 52dp / 16dp control spec.
 */
@Composable
fun VyraButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: VyraButtonStyle = VyraButtonStyle.Primary,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
) {
    when (style) {
        VyraButtonStyle.Primary -> Button(
            onClick = onClick,
            modifier = modifier.height(52.dp),
            enabled = enabled,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = VyraActionCyan,
                contentColor = VyraOnAction,
            ),
        ) { VyraButtonContent(text, leadingIcon) }

        VyraButtonStyle.Secondary -> OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(52.dp),
            enabled = enabled,
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
        ) { VyraButtonContent(text, leadingIcon) }

        VyraButtonStyle.Ghost -> TextButton(
            onClick = onClick,
            modifier = modifier.height(52.dp),
            enabled = enabled,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        ) { VyraButtonContent(text, leadingIcon) }
    }
}

@Composable
private fun VyraButtonContent(text: String, leadingIcon: ImageVector?) {
    if (leadingIcon != null) {
        Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
    }
    Text(text = text, style = MaterialTheme.typography.labelLarge)
}
