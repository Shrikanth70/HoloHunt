package com.vyra.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.vyra.app.feature.capture.CaptureRoute
import com.vyra.app.feature.create.CreateScreen
import com.vyra.app.feature.home.HomeScreen
import com.vyra.app.feature.library.LibraryScreen
import com.vyra.app.feature.preview.MediaPreviewRoute
import com.vyra.app.feature.profile.ProfileScreen

/**
 * The single Navigation-Compose graph for VYRA's top-level tabs and media flows.
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
            HomeScreen(
                onStartCreating = { navController.navigate("capture") },
            )
        }
        composable(TopLevelDestination.CREATE.route) {
            CreateScreen(
                onModeSelected = { _ -> navController.navigate("capture") },
            )
        }
        composable(TopLevelDestination.LIBRARY.route) { LibraryScreen() }
        composable(TopLevelDestination.PROFILE.route) { ProfileScreen() }

        // Phase 1 Media Capture Flow
        composable("capture") {
            CaptureRoute(
                onMediaCaptured = { mediaItem ->
                    val encodedUri = Uri.encode(mediaItem.uri.toString())
                    navController.navigate("preview?mediaUri=$encodedUri&mediaType=${mediaItem.type.name}")
                },
                onClose = { navController.popBackStack() },
            )
        }

        // Phase 1 Media Preview Flow
        composable(
            route = "preview?mediaUri={mediaUri}&mediaType={mediaType}",
            arguments = listOf(
                navArgument("mediaUri") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("mediaType") {
                    type = NavType.StringType
                    defaultValue = "IMAGE"
                },
            ),
        ) {
            MediaPreviewRoute(
                onAccept = { _, _ ->
                    // Media accepted — for now navigate to Home/Library until Phase 2 AI pipeline
                    navController.popBackStack(TopLevelDestination.HOME.route, inclusive = false)
                },
                onRetake = {
                    navController.popBackStack()
                },
            )
        }
    }
}
