package com.vyra.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vyra.app.feature.create.CreateScreen
import com.vyra.app.feature.home.HomeScreen
import com.vyra.app.feature.library.LibraryScreen
import com.vyra.app.feature.profile.ProfileScreen

/**
 * The single Navigation-Compose graph for VYRA's top-level tabs. Feature
 * screens are stateless entry points here; deeper flows (capture, extraction,
 * AR, editor) are added to this graph in their respective phases.
 */
@Composable
fun VyraNavHost(
    navController: NavHostController,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.HOME.route,
        modifier = modifier,
    ) {
        composable(TopLevelDestination.HOME.route) {
            HomeScreen(onStartCreating = { onNavigateToDestination(TopLevelDestination.CREATE) })
        }
        composable(TopLevelDestination.CREATE.route) { CreateScreen() }
        composable(TopLevelDestination.LIBRARY.route) { LibraryScreen() }
        composable(TopLevelDestination.PROFILE.route) { ProfileScreen() }
    }
}
