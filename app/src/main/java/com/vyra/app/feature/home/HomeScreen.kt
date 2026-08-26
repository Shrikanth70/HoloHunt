package com.vyra.app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vyra.app.R
import com.vyra.app.core.designsystem.component.CreateOptionCard
import com.vyra.app.core.designsystem.component.VyraButton
import com.vyra.app.core.designsystem.component.VyraEmptyState
import com.vyra.app.feature.create.CreateMode

/**
 * VYRA home: brand header, a neon hero with the primary CTA, the creation-mode
 * teasers, and the (currently empty) creations feed. All navigation actions
 * funnel through [onStartCreating] for now; per-mode routing arrives with the
 * flows themselves in later phases.
 */
@Composable
fun HomeScreen(
    onStartCreating: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        HomeTopBar()
        HomeHero(onStartCreating = onStartCreating)
        Spacer(Modifier.height(32.dp))
        CreateSection(onOptionClick = onStartCreating)
        Spacer(Modifier.height(32.dp))
        CreationsSection()
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = stringResource(R.string.home_avatar_cd),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp),
            )
        }
        Text(
            text = stringResource(R.string.home_brand_cd),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        // Balances the avatar so the wordmark stays centered.
        Spacer(Modifier.size(40.dp))
    }
}

@Composable
private fun HomeHero(onStartCreating: () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(340.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Brush.verticalGradient(listOf(scheme.surfaceContainerHigh, scheme.surface))),
    ) {
        // Soft neon glow radiating from the center.
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            com.vyra.app.core.designsystem.theme.VyraGlow.copy(alpha = 0.22f),
                            Color.Transparent,
                        ),
                    ),
                ),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp),
        ) {
            Text(
                text = stringResource(R.string.home_hero_title),
                style = MaterialTheme.typography.displayLarge,
                color = scheme.onSurface,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.home_hero_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = scheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(24.dp))
            VyraButton(
                text = stringResource(R.string.home_cta_create),
                onClick = onStartCreating,
                leadingIcon = Icons.Outlined.AddCircle,
            )
        }
    }
}

@Composable
private fun CreateSection(onOptionClick: () -> Unit) {
    Text(
        text = stringResource(R.string.home_section_create),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(horizontal = 20.dp),
    )
    Spacer(Modifier.height(16.dp))
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(CreateMode.entries) { mode ->
            CreateOptionCard(
                title = stringResource(mode.titleRes),
                description = stringResource(mode.subtitleRes),
                icon = mode.icon,
                modifier = Modifier.width(220.dp),
                onClick = onOptionClick,
            )
        }
    }
}

@Composable
private fun CreationsSection() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = stringResource(R.string.home_section_creations),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(16.dp))
        VyraEmptyState(text = stringResource(R.string.home_creations_empty))
    }
}
