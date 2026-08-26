package com.vyra.app.feature.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vyra.app.R
import com.vyra.app.core.designsystem.component.CreateOptionCard

/**
 * The Create hub: presents the three creation modes. The cards are
 * presentational in this phase — each mode's flow (capture, extraction, AR,
 * remix) is wired up in its own phase, so we deliberately do not attach an
 * action that goes nowhere.
 */
@Composable
fun CreateScreen(
    onModeSelected: (CreateMode) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.create_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.create_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(24.dp))
        CreateMode.entries.forEach { mode ->
            CreateOptionCard(
                title = stringResource(mode.titleRes),
                description = stringResource(mode.subtitleRes),
                icon = mode.icon,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onModeSelected(mode) },
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}
