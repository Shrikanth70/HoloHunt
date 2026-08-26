package com.vyra.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vyra.app.navigation.TopLevelDestination
import com.vyra.app.navigation.VyraBottomBar
import com.vyra.app.navigation.VyraNavHost

/**
 * Root composable: hosts the nav controller, the bottom bar, and the nav graph.
 * Tab switches use the standard single-top + save/restore pattern so each tab
 * keeps its own back stack and scroll state.
 */
@Composable
fun VyraApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val onNavigate: (TopLevelDestination) -> Unit = { destination ->
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            VyraBottomBar(currentRoute = currentRoute, onNavigate = onNavigate)
        },
    ) { innerPadding ->
        VyraNavHost(
            navController = navController,
            onNavigateToDestination = onNavigate,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
