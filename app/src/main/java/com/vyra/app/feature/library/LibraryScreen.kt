package com.vyra.app.feature.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vyra.app.R
import com.vyra.app.core.designsystem.component.VyraEmptyState

/**
 * The Library lists the user's saved subjects. It's genuinely empty until the
 * extraction flow exists, so we show an honest empty state rather than fake
 * placeholder items.
 */
@Composable
fun LibraryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.library_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(24.dp))
        VyraEmptyState(
            text = stringResource(R.string.library_empty),
            icon = Icons.Outlined.PhotoLibrary,
        )
    }
}
